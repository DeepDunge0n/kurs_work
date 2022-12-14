package org.source.controllers;

import java.net.ConnectException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.fr2eman.send.DataResponse;
import org.source.ApplicationUI;
import org.source.models.Product;
import org.source.network.ServerFacade;
import org.source.service.CacheService;

public class CreatorProductPanelController {
	
	private static final String m_regNumber = "^[0-9]+$";
	private static final String m_regPrice = "^[0-9]+$";
	
	private String m_reason;
	
	private static CreatorProductPanelController instance 
				= new CreatorProductPanelController();
	
	public static CreatorProductPanelController getInstance() {
		return instance;
	}
	
	private CreatorProductPanelController() {
		m_reason = "";
	}
	
	public void add() {
		if(!validator()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					m_reason);
					return;
		}
		DataResponse data = null;
		try {
			data = ServerFacade.getInstance().requestAddProduct();
		} catch(ConnectException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), "Нет соединения с сервером",
					"Ошибка соединения", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(data.getError()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
			data.getMessage());
			return;
		} else {
			CacheService.getInstance().addProductsList(new Product(
					Integer.valueOf(data.getData().get("id_product")),
					data.getData().get("name_product"),
					Integer.valueOf(data.getData().get("price_product")),
					Integer.valueOf(data.getData().get("number_product"))));
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					data.getMessage());
			ApplicationUI.getInstance().pop();
		}
	}
	
	public void back() {
		ApplicationUI.getInstance().pop();
	}
	
	public boolean validator() {
		if(CacheService.getInstance().getNameProduct().isEmpty()) {
			m_reason = "Не введено наименование товара";
			return false;
		}
		if(CacheService.getInstance().getNumberProduct().isEmpty()) {
			m_reason = "Не введено количество товара";
			return false;
		}
		Pattern regEmail = Pattern.compile(m_regNumber);
		Matcher matcher = regEmail.matcher(CacheService.getInstance().getNumberProduct());
		if(!matcher.matches()) {
			m_reason = "Количество товара введёно не корректно";
			return false;
		}
		if(CacheService.getInstance().getPriceProduct().isEmpty()) {
			m_reason = "Не введена цена товара";
			return false;
		}
		regEmail = Pattern.compile(m_regPrice);
		matcher = regEmail.matcher(CacheService.getInstance().getPriceProduct());
		if(!matcher.matches()) {
			m_reason = "Цена товара введёна не корректно";
			return false;
		} else {
			if(Integer.valueOf(CacheService.getInstance().getPriceProduct()) <= 0) {
				m_reason = "Цена товара должна быть положительным числом";
				return false;
			}
		}
		return true;
	}
}
