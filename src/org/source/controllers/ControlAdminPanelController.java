package org.source.controllers;

import java.net.ConnectException;

import javax.swing.JOptionPane;

import org.fr2eman.send.DataResponse;
import org.source.ApplicationUI;
import org.source.service.CacheService;
import org.source.service.DataService;
import org.source.views.AdminStatisticsPanel;
import org.source.views.AllHistoryOrdersPanel;
import org.source.views.ControlPanel;
import org.source.views.ControlProductsPanel;
import org.source.views.ControlUsersPanel;

import org.source.models.Order;
import org.source.models.Product;
import org.source.models.User;
import org.source.network.ServerFacade;

public class ControlAdminPanelController {
	
	private static ControlAdminPanelController instance = new ControlAdminPanelController();
	
	public static ControlAdminPanelController getInstance() {
		return instance;
	}

	public void products() {
		
		CacheService.getInstance().setSearchString("");
		CacheService.getInstance().setSortColumn("");
		CacheService.getInstance().setTypeSort("");
		DataResponse response = null;
		try {
			response = ServerFacade.getInstance().requestProducts();
		} catch(ConnectException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), "Нет соединения с сервером",
					"Ошибка соединения", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(response.getError()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					response.getMessage());
			return;
		}
		CacheService.getInstance().clearProducts();
		int numberProducts = Integer.valueOf(response.getData().get("number"));
		for(int i = 1; i <= numberProducts; i++) {
			int id = Integer.valueOf(response.getData().get("id_product" + i));
			String name = response.getData().get("name_product" + i);
			int number = Integer.valueOf(response.getData().get("number_product" + i));
			int price = Integer.valueOf(response.getData().get("price_product" + i));
			CacheService.getInstance().addProductsList(new Product(
					id, name, price, number));
		}
		ApplicationUI.getInstance().push(new ControlProductsPanel());
	}
	
	public void clients() {

		CacheService.getInstance().setSearchString("");
		
		DataResponse response = null;
		try {
			response = ServerFacade.getInstance().requestGetAllUsers();
		} catch(ConnectException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), "Нет соединения с сервером",
					"Ошибка соединения", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(response.getError()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					response.getMessage());
			return;
		}
		CacheService.getInstance().clearListUsers();
		int numberProducts = Integer.valueOf(response.getData().get("number"));
		for(int i = 1; i <= numberProducts; i++) {
			int id = Integer.valueOf(response.getData().get("id_user" + i));
			String login = response.getData().get("login" + i);
			String f_name = response.getData().get("first_name" + i);
			String s_name = response.getData().get("second_name" + i);
			String m_name = response.getData().get("middle_name" + i);
			String email = response.getData().get("email" + i);
			CacheService.getInstance().addInListUsers(new User(
					id, login, f_name, s_name, m_name, email));
		}
		ApplicationUI.getInstance().push(new ControlUsersPanel());
	}
	
	public void orders() {
		
		CacheService.getInstance().setSearchString("");
		CacheService.getInstance().setFirstDate("");
		CacheService.getInstance().setLastDate("");
		CacheService.getInstance().setIsAllOrders(true);
		
		DataResponse data = null;
		try {
			data = ServerFacade.getInstance().requestGetAllOrders();
		} catch(ConnectException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), "Нет соединения с сервером",
					"Ошибка соединения", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(data.getError()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
			data.getMessage());
		} else {
			int numberOrders = Integer.valueOf(data.getData().get("number"));
			for(int i = 1; i <= numberOrders; ++i) {
				CacheService.getInstance().addInListOrders(new Order(
						Integer.valueOf(data.getData().get("id" + i)),
						Integer.valueOf(data.getData().get("user" + i)),
						data.getData().get("date" + i),
						Integer.valueOf(data.getData().get("state" + i))));
			}
		}
		
		ApplicationUI.getInstance().push(new AllHistoryOrdersPanel());
		
	}
	
	public void statistic() {
		ApplicationUI.getInstance().push(new AdminStatisticsPanel());
	}
	
	public void close() {
		DataService.getInstance().setLoginCurrentUser("");
		DataService.getInstance().setPassCurrentUser("");
		ApplicationUI.getInstance().pop();
		ApplicationUI.getInstance().push(new ControlPanel());
	}
	
	public void exit() {
		System.exit(0);
	}
}
