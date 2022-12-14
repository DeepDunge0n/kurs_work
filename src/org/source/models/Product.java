package org.source.models;

public class Product {
	
	private int m_id;
	private String m_name;
	private int m_price;
	private int m_number;
	private String m_description;
	private boolean m_inStock;
	
	public Product() {
		m_id = 0;
		m_name = "";
		m_description = "";
		m_number = 0;
		m_price = 0;
	}
	public Product(int id) {
		m_id = id;
		m_name = "";
		m_price = 0;
		m_number = 0;
		m_inStock = false;
	}
	
	public Product(int id, String name, int price) {
		m_id = id;
		m_name = new String(name);
		m_price = price;
		m_number = 0;
	}
	
	public Product(int id, String name, int price, boolean inStock) {
		m_id = id;
		m_name = new String(name);
		m_price = price;
		m_number = 0;
		m_inStock = inStock;
	}
	public Product(int id, String name, int price, int number) {
		m_id = id;
		m_name = new String(name);
		m_price = price;
		m_number = number;
		m_inStock = number == 0 ? false : true;
	}
	
	public Product(int id, String description, String name, int price, int number) {
		m_id = id;
		m_name = new String(name);
		m_description = new String(description);
		m_price = price;
		m_number = number;
		m_inStock = number == 0 ? false : true;
	}
	
	public int getId() {
		return m_id;
	}
	public void setId(int id) {
		m_id = id;
	}
	public String getName() {
		return m_name;
	}
	public void setName(String name) {
		m_name = name;
	}
	public int getPrice() {
		return m_price;
	}
	public void setPrice(int price) {
		m_price = price;
	}
	public boolean isInStock() {
		return m_inStock;
	}
	public void setInStock(boolean inStock) {
		m_inStock = inStock;
	}
	public int getNumber() {
		return m_number;
	}
	public void setNumber(int number) {
		m_number = number;
	}
	public String getDescription() {
		return m_description;
	}
	public void setDescription(String description) {
		m_description = description;
	}
	
	@Override
	public boolean equals(Object product) {
		if(product == null) return false;
		if(product instanceof User) {
			if(this.m_id != ((Product)product).m_id) return false;
		} else return false;
		return true;
	}
}
