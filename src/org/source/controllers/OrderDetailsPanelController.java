package org.source.controllers;

import java.net.ConnectException;
import javax.swing.JOptionPane;

import org.fr2eman.send.DataResponse;
import org.source.ApplicationUI;
import org.source.network.ServerFacade;
import org.source.service.CacheService;

public class OrderDetailsPanelController {
	
	private static OrderDetailsPanelController instance = new OrderDetailsPanelController();
	
	public static OrderDetailsPanelController getInstance() {
		return instance;
	}

	public void description() {
		
		DataResponse response = null;
		try {
			response = ServerFacade.getInstance().requestDescriptionProduct();
		} catch(ConnectException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), "Нет соединения с сервером",
					"Ошибка соединения", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(response.getError()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					response.getMessage());
		} else {
			String description = "<html> Описание товара: ";
			String []substringDescription = response.getData().get("description").split(" ");
			int index = 55;
			for(String item : substringDescription) {
				description += item + " ";
				if(description.length() > index) {
					description += "<br>";
					index += 55;
				}
			}
			description += "</html>";
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					description, "Описание товара", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void cancel() {
		
		DataResponse response = null;
		try {
			response = ServerFacade.getInstance().requestCancelOrder();
		} catch(ConnectException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), "Нет соединения с сервером",
					"Ошибка соединения", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(response.getError()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					response.getMessage());
		} else {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					response.getMessage());
			CacheService.getInstance().removeOrderFromListOrders(
					CacheService.getInstance().getCurrentOrder().getId());
			
			ApplicationUI.getInstance().pop();
		}
	}
	
	public void back() {
		ApplicationUI.getInstance().pop();
	}
}
