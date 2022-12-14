package org.source.controllers;

import java.net.ConnectException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.fr2eman.send.DataResponse;
import org.source.ApplicationUI;
import org.source.network.ServerFacade;
import org.source.service.CacheService;
import org.source.service.DataService;
import org.source.views.BasketPanel;
import org.source.views.dialogs.ShopDialog;

public class BasketPanelController {
	
	final private static int ADD_BUTTON = 1;
	
	private static BasketPanelController instance = new BasketPanelController();
	
	public static BasketPanelController getInstance() {
		return instance;
	}

	public void change(BasketPanel panel) {
		JTextField inputNumber = new JTextField();

		ShopDialog dialog = new ShopDialog();
		dialog.setTitle("Количество");
		dialog.setRootPane(ApplicationUI.getInstance());
		dialog.addComponent((new JLabel("Введите количество товара: ")));
		dialog.addComponent(inputNumber);
		dialog.setOptions(new String[] {"Отменить", "Добавить"});
		if(dialog.show() == ADD_BUTTON) {
			try {
				int numberProduct = Integer.valueOf(inputNumber.getText());
				if(numberProduct <= 0) {
					JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
							"Вы некоректно ввели кол-во товара. Изменения не внесены", "Ошибка", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int idProduct = CacheService.getInstance().getIdSelectedProduct();
				DataService.getInstance().deleteProductFromBasket(idProduct);
				DataService.getInstance().addProductInBasket(idProduct, numberProduct);
				panel.updateBasket();
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					"Вы некоректно ввели кол-во товара. Товар не добавлен", "Ошибка", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void delete(BasketPanel panel) {
		
		DataService.getInstance().deleteProductFromBasket(
				CacheService.getInstance().getIdSelectedProduct());
		
		panel.updateBasket();
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
	
	public void makeOrder() {
		
		if(DataService.getInstance().isEmptyBasket()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					"В листе заказов нет товаров.");
		} else {
			
			DataResponse response = null;
			try {
				response = ServerFacade.getInstance().requestMakeOrder();
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
						"Заказ № " + response.getData().get("order") 
						+ " принят", "Подтверждение заказа", JOptionPane.INFORMATION_MESSAGE);
				DataService.getInstance().clearBasket();
				back();
			}
		}
	}
	
	public void back() {
		ApplicationUI.getInstance().pop();
	}
}
