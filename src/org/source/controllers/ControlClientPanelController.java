package org.source.controllers;

import java.net.ConnectException;

import javax.swing.JOptionPane;

import org.fr2eman.send.DataResponse;
import org.source.ApplicationUI;
import org.source.models.Product;
import org.source.network.ServerFacade;
import org.source.service.CacheService;
import org.source.service.DataService;
import org.source.views.ClientPersonalPanel;
import org.source.views.ControlPanel;
import org.source.views.OrderPanel;
import org.source.views.ProductsPanel;

public class ControlClientPanelController {
	
	private static ControlClientPanelController instance = new ControlClientPanelController();
	
	public static ControlClientPanelController getInstance() {
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
		ApplicationUI.getInstance().push(new ProductsPanel());
	}
	
	public void order() {
		
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

		ApplicationUI.getInstance().push(new OrderPanel());
	}
	
	public void personal() {
		DataResponse response = null;
		try {
			response = ServerFacade.getInstance().requestDadasUser();
		} catch(ConnectException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), "Нет соединения с сервером",
					"Ошибка соединения", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(response.getError()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					response.getMessage());
			return;
		} else {
			try {
				CacheService.getInstance().setId(Integer.valueOf(response.getData().get("id")));
			} catch(NumberFormatException e) {}
			CacheService.getInstance().setSecondName(response.getData().get("second_name"));
			CacheService.getInstance().setFirstName(response.getData().get("first_name"));
			CacheService.getInstance().setMiddleName(response.getData().get("middle_name"));
			CacheService.getInstance().setEmail(response.getData().get("email"));
			CacheService.getInstance().setAccessUser(Integer.valueOf(response.getData().get("access")));
		}
		ApplicationUI.getInstance().push(new ClientPersonalPanel());
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
