package org.source.controllers;

import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import org.fr2eman.send.DataResponse;
import org.source.ApplicationUI;
import org.source.models.Order;
import org.source.models.Product;
import org.source.models.User;
import org.source.network.ServerFacade;
import org.source.service.CacheService;
import org.source.views.ControlOrderPanel;
import org.source.views.base.BasePanel;

public class AdminStatisticsPanelController {
	
	private static AdminStatisticsPanelController instance = new AdminStatisticsPanelController();
	
	public static AdminStatisticsPanelController getInstance() {
		return instance;
	}
	
	public void statisticProducts(BasePanel panel, Date beginDate, Date endDate) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		CacheService.getInstance().setFirstDate(
				beginDate != null ? dateFormat.format(beginDate) : "");
		CacheService.getInstance().setLastDate(
				endDate != null ? dateFormat.format(endDate) : "");
		CacheService.getInstance().clearListOrders();
		
		try {
			if(Integer.valueOf(CacheService.getInstance().getSearchString()) < 1) {
				JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
						"Количество должно быть положительным числом");
				return;
			}
		} catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					"Не правильно введено количество");
			return;
		}
		
		DataResponse data = null;
		try {
			data =  ServerFacade.getInstance().requestStatisticProducts();
		} catch(ConnectException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), "Нет соединения с сервером",
					"Ошибка соединения", JOptionPane.ERROR_MESSAGE);
			return;
		}
		CacheService.getInstance().clearProducts();
		if(data.getError()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
			data.getMessage());
		} else {
			int numberOrders = Integer.valueOf(data.getData().get("number"));
			for(int i = 1; i <= numberOrders; ++i) {
				CacheService.getInstance().addProductsList(new Product(
						Integer.valueOf(data.getData().get("id_product" + i)),
						data.getData().get("description" + i),
						data.getData().get("name_product" + i),
						Integer.valueOf(data.getData().get("price_product" + i)),
						Integer.valueOf(data.getData().get("number_product" + i))));
			}
		}
		panel.updatePanel();
	}
	
	public void statisticUsers(BasePanel panel, Date beginDate, Date endDate) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		CacheService.getInstance().setFirstDate(
				beginDate != null ? dateFormat.format(beginDate) : "");
		CacheService.getInstance().setLastDate(
				endDate != null ? dateFormat.format(endDate) : "");
		CacheService.getInstance().clearListOrders();
		
		try {
			if(Integer.valueOf(CacheService.getInstance().getSearchString()) < 1) {
				JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
						"Количество должно быть положительным числом");
				return;
			}
		} catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					"Не правильно введено количество");
			return;
		}
		
		DataResponse data = null;
		try {
			data =  ServerFacade.getInstance().requestStatisticUsers();
		} catch(ConnectException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), "Нет соединения с сервером",
					"Ошибка соединения", JOptionPane.ERROR_MESSAGE);
			return;
		}
		CacheService.getInstance().clearListUsers();
		if(data.getError()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
			data.getMessage());
		} else {
			int numberUsers = Integer.valueOf(data.getData().get("number"));
			for(int i = 1; i <= numberUsers; ++i) {
				CacheService.getInstance().addInListUsers(new User(
						Integer.valueOf(data.getData().get("id_user" + i)),
						data.getData().get("login" + i),
						Integer.valueOf(data.getData().get("number_orders" + i)),
						Integer.valueOf(data.getData().get("summ_orders" + i))));
			}
		}
		panel.updatePanel();
	}
	
	public void statisticOrders(BasePanel panel, Date beginDate, Date endDate) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		CacheService.getInstance().setFirstDate(
				beginDate != null ? dateFormat.format(beginDate) : "");
		CacheService.getInstance().setLastDate(
				endDate != null ? dateFormat.format(endDate) : "");
		CacheService.getInstance().clearListOrders();
		
		try {
			if(Integer.valueOf(CacheService.getInstance().getSearchString()) < 1) {
				JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
						"Количество должно быть положительным числом");
				return;
			}
		} catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					"Не правильно введено количество");
			return;
		}
		
		DataResponse data = null;
		try {
			data =  ServerFacade.getInstance().requestStatisticOrders();
		} catch(ConnectException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), "Нет соединения с сервером",
					"Ошибка соединения", JOptionPane.ERROR_MESSAGE);
			return;
		}
		CacheService.getInstance().clearListOrders();
		if(data.getError()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
			data.getMessage());
		} else {
			int numberOrders = Integer.valueOf(data.getData().get("number"));
			for(int i = 1; i <= numberOrders; ++i) {
				CacheService.getInstance().addInListOrders(new Order(
						Integer.valueOf(data.getData().get("id_order" + i)),
						Integer.valueOf(data.getData().get("user" + i)),
						data.getData().get("date" + i),
						Integer.valueOf(data.getData().get("state" + i)),
						Integer.valueOf(data.getData().get("price" + i))));
			}
		}
		panel.updatePanel();
	}
	
	public void back() {
		CacheService.getInstance().clearListOrders();
		CacheService.getInstance().clearListUsers();
		CacheService.getInstance().clearProducts();
		ApplicationUI.getInstance().pop();
	}
}
