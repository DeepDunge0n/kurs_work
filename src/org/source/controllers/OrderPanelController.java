package org.source.controllers;

import java.net.ConnectException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.fr2eman.send.DataResponse;
import org.source.ApplicationUI;
import org.source.models.Product;
import org.source.network.ServerFacade;
import org.source.service.CacheService;
import org.source.service.DataService;
import org.source.views.BasketPanel;
import org.source.views.base.BasePanel;
import org.source.views.dialogs.ShopDialog;

public class OrderPanelController {
	
	final private static int ADD_BUTTON = 1; 
	
	private static OrderPanelController instance = new OrderPanelController();
	
	public static OrderPanelController getInstance() {
		return instance;
	}

	public void addProduct(int idProduct) {
		
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
							"Вы некоректно ввели кол-во товара. Товар не добавлен", "Ошибка", JOptionPane.ERROR_MESSAGE);
					return;
				}
				DataService.getInstance().addProductInBasket(idProduct, numberProduct);
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					"Вы некоректно ввели кол-во товара. Товар не добавлен", "Ошибка", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void openBasket() {
		
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
		ApplicationUI.getInstance().push(new BasketPanel());
	}
	
	public void search(BasePanel panel) {
		
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
		panel.updatePanel();
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
	
	public void back() {
		ApplicationUI.getInstance().pop();
	}
}
