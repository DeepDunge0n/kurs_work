package org.source.controllers;

import java.net.ConnectException;

import javax.swing.JOptionPane;

import org.fr2eman.send.DataResponse;
import org.source.ApplicationUI;
import org.source.models.Product;
import org.source.network.ServerFacade;
import org.source.service.CacheService;
import org.source.views.AuthorizationClientPanel;
import org.source.views.AuthorizationEmployeePanel;
import org.source.views.ProductsPanel;

public class ControlPanelController {
	
	private static ControlPanelController instance = new ControlPanelController();
	
	public static ControlPanelController getInstance() {
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

	public void authorizationClients() {
		ApplicationUI.getInstance().push(new AuthorizationClientPanel());
	}
	
	public void authorizationEmployees() {
		ApplicationUI.getInstance().push(new AuthorizationEmployeePanel());
	}

	public void exit() {
		System.exit(0);
	}
	
}
