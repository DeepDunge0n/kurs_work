package org.source.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.source.models.Order;
import org.source.models.Product;
import org.source.models.User;

public class CacheService {

	private static CacheService instance = new CacheService();
	
	private int m_id;
	private String m_login;
	private String m_password;
	private String m_repetitionPass;
	private String m_secondName;
	private String m_firstName;
	private String m_middleName;
	private String m_email;
	private int m_accessUser;
	private String m_sortColumn;
	private String m_searchString;
	private String m_typeSort;
	private List<Product> m_listProducts;
	private List<Order> m_listOrders;
	private List<User> m_listUsers;
	private int m_idSelectedProduct;
	private int m_idSelectedOrder;
	private int m_idSelectedUser;
	private int m_statusOrder;
	private User m_currentUser;
	private String m_firstDate;
	private String m_lastDate;
	private Order m_currentOrder;
	private String m_nameProduct;
	private String m_numberProduct;
	private String m_priceProduct;
	private String m_descriptionProduct;
	private boolean m_isAllOrders;
	private boolean m_isStatisticForYear;
	private int m_statisticYear;
	private int m_statisticMonth;
	private List<Number> m_statisticsOrders;
	
	public static CacheService getInstance() {
		return instance;
	}
	
	public CacheService() {
		m_listProducts = new ArrayList<Product>();
		m_currentUser = new User();
		m_listOrders = new ArrayList<Order>();
		m_listUsers = new ArrayList<User>();
		m_statisticsOrders = new ArrayList<Number>();
	}
	
	public int getId() {
		return this.m_id;
	}
	
	public void setId(int id) {
		this.m_id = id;
	}
	
	public String getLogin() {
		return this.m_login;
	}
	
	public void setLogin(final String login) {
		this.m_login = login;
	}
	
	public String getPassword() {
		return this.m_password;
	}
	
	public void setPassword(final String pass) {
		this.m_password = pass;
	}
	
	public String getFirstName() {
		return this.m_firstName;
	}
	
	public void setFirstName(String firstName) {
		this.m_firstName = firstName;
	}
	
	public String getSecondName() {
		return this.m_secondName;
	}
	
	public void setSecondName(String secondName) {
		this.m_secondName = secondName;
	}
	
	public String getMiddleName() {
		return this.m_middleName;
	}
	
	public void setMiddleName(String middleName) {
		this.m_middleName = middleName;
	}
	
	public String getEmail() {
		return this.m_email;
	}
	
	public void setEmail(String email) {
		this.m_email = email;
	}
	
	public String getRepetitionPass() {
		return this.m_repetitionPass;
	}
	
	public void setRepetitionPass(String pass) {
		this.m_repetitionPass = pass;
	}
	
	public int getAccessUser() {
		return this.m_accessUser;
	}
	
	public void setAccessUser(int access) {
		this.m_accessUser = access;
	}
	
	public String getSearchString() {
		return m_searchString;
	}
	public void setSearchString(String searchString) {
		m_searchString = searchString;
	}
	
	public String getSortColumn() {
		return m_sortColumn;
	}
	public void setSortColumn(String sortColumn) {
		m_sortColumn = sortColumn;
	}
	public String getTypeSort() {
		return m_typeSort;
	}
	public void setTypeSort(String typeSort) {
		m_typeSort = typeSort;
	}
	public List<Product> getListProducts() {
		return m_listProducts;
	}
	public void setListProducts(List<Product> listProducts) {
		m_listProducts.clear();
		m_listProducts.addAll(listProducts);
	}
	public void clearProducts() {
		m_listProducts.clear();
	}
	public void addProductsList(Product product) {
		m_listProducts.add(product);
	}
	public int getIdSelectedProduct() {
		return m_idSelectedProduct;
	}
	public void setIdSelectedProduct(int id) {
		m_idSelectedProduct = id;
	}
	public int getIdSelectedOrder() {
		return m_idSelectedOrder;
	}
	public void setIdSelectedOrder(int id) {
		m_idSelectedOrder = id;
	}
	public User getCurrentUser() {
		return m_currentUser;
	}
	public void setCurrentUser(User user) {
		m_currentUser = user;
	}
	public String getFirstDate() {
		return m_firstDate;
	}
	public void setFirstDate(String date) {
		m_firstDate = date;
	}
	public String getLastDate() {
		return m_lastDate;
	}
	public void setLastDate(String date) {
		m_lastDate = date;
	}
	public List<Order> getListOrders() {
		return m_listOrders;
	}
	public void setListOrders(List<Order> list) {
		m_listOrders = new ArrayList<Order>(list);
	}
	public void addInListOrders(Order newOrder) {
		m_listOrders.add(newOrder);
	}
	public void removeOrderFromListOrders(int idOrder) {
		for(Order order : m_listOrders) {
			if(order.getId() == idOrder) {
				m_listOrders.remove(order);
				break;
			}
		}
	}
	public List<User> getListUsers() {
		return m_listUsers;
	}
	public void setListUsers(List<User> list) {
		m_listUsers = new ArrayList<User>(list);
	}
	public void addInListUsers(User newUser) {
		m_listUsers.add(newUser);
	}
	public void removeUserFromListUsers(int idUser) {
		for(User user : m_listUsers) {
			if(user.getId() == idUser) {
				m_listUsers.remove(user);
				break;
			}
		}
	}
	public void clearListOrders() {
		m_listOrders.clear();
	}
	public void clearListUsers() {
		m_listUsers.clear();
	}
	public Order getCurrentOrder() {
		return m_currentOrder;
	}
	public void setCurrentOrder(Order order) {
		m_currentOrder = order;
	}
	public String getNameProduct() {
		return m_nameProduct;
	}
	public void setNameProduct(String name) {
		m_nameProduct = name;
	}
	public String getNumberProduct() {
		return m_numberProduct;
	}
	public void setNumberProduct(String number) {
		m_numberProduct = number;
	}
	public String getPriceProduct() {
		return m_priceProduct;
	}
	public void setPriceProduct(String price) {
		m_priceProduct = price;
	}
	public String getDescriptionProduct() {
		return m_descriptionProduct;
	}
	public void setDescriptionProduct(String description) {
		m_descriptionProduct = description;
	}
	public int getOrderStatus() {
		return m_statusOrder;
	}
	public void setOrderStatus(int status) {
		m_statusOrder = status;
	}
	public int getIdSelectedUser() {
		return m_idSelectedUser;
	}
	public void setIdSelectedUser(int id) {
		m_idSelectedUser = id;
	}
	public boolean getIsAllOrders() {
		return m_isAllOrders;
	}
	public void setIsAllOrders(boolean isAllOrders) {
		m_isAllOrders = isAllOrders;
	}
	public int getStatisticYear() {
		return m_statisticYear;
	}
	public void setStatisticYear(int year) {
		m_statisticYear = year;
	}
	public int getStatisticMonth() {
		return m_statisticMonth;
	}
	public void setStatisticMonth(int month) {
		m_statisticMonth = month;
	}
	public List<Number> getStatisticOrders() {
		return m_statisticsOrders;
	}
	public void setStatisticOrders(List<Number> statistic) {
		m_statisticsOrders = statistic;
	}
	public boolean getIsStatisticForYear() {
		return m_isStatisticForYear;
	}
	public void setIsStatisticForYear(boolean isStatisticForYear) {
		m_isStatisticForYear = isStatisticForYear;
	}
}
