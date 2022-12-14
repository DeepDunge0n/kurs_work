package org.source.service;

import org.source.models.Basket;

public class DataService {
	
	private String m_nameServer;
	private int m_port;
	private Basket m_basket;
	private String m_loginCurrentUser;
	private String m_passCurrentUser;
	
	private static DataService instance = new DataService();
	
	public static DataService getInstance() {
		return instance;
	}
	
	private DataService() {
		m_nameServer = new String();
		m_port = 0;
		m_basket = new Basket();
	}
	
	public String getNameServer() {
		return m_nameServer;
	}
	public void setNameServer(String name) {
		m_nameServer = new String(name);
	}
	
	public int getPort() {
		return m_port;
	}
	public void setPort(int port) {
		m_port = port;
	}

	public void addProductInBasket(int idProduct, int numberProduct) {
		m_basket.addProduct(idProduct, numberProduct);
	}
	
	public Basket getBasket() {
		return m_basket;
	}
	
	public void clearBasket() {
		m_basket.clearBasket();
	}
	
	public boolean isEmptyBasket() {
		return m_basket.getDataOrder().isEmpty();
	}
	
	public void deleteProductFromBasket(int idProduct) {
		m_basket.getDataOrder().remove(idProduct);
	}
	
	public String getLoginCurrentUser() {
		return m_loginCurrentUser;
	}
	public void setLoginCurrentUser(String login) {
		m_loginCurrentUser = new String(login);
	}
	public String getPassCurrentUser() {
		return m_passCurrentUser;
	}
	public void setPassCurrentUser(String pass) {
		m_passCurrentUser = new String(pass);
	}
}
