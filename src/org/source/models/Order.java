package org.source.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {
	
	public static final int ORDER_IS_PREPARING = 0;
	public static final int ORDER_IS_SENT = 1;
	public static final int ORDER_IS_COMPLETED = 2;
	
	private int m_id;
	private int m_user;
	private Date m_date;
	private int m_state;
	private int m_price;
	
	public Order() {
		m_id = 0;
		m_user = 0;
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-mm-dd"); 
		try {
			this.m_date = formatDate.parse("0000-00-00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		m_state = 0;
		m_price = 0;
	}
	
	public Order(int id, int user, String date, int state) {
		m_id = id;
		m_user = user;
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-mm-dd"); 
		try {
			this.m_date = formatDate.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			try {
				this.m_date = formatDate.parse("0000-00-00");
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		m_state = state;
		m_price = 0;
	}
	
	public Order(int id, int user, String date, int state, int price) {
		m_id = id;
		m_user = user;
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-mm-dd"); 
		try {
			this.m_date = formatDate.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			try {
				this.m_date = formatDate.parse("0000-00-00");
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		m_state = state;
		m_price = price;
	}
	
	public int getId() {
		return m_id;
	}
	public void setId(int id) {
		m_id = id;
	}
	public int getUser() {
		return m_user;
	}
	public void setUser(int user) {
		m_user = user;
	}
	public Date getDate() {
		return m_date;
	}
	public void setDate(String date) {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-mm-dd"); 
		try {
			this.m_date = formatDate.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public String getStringDate() {
		return (new SimpleDateFormat("yyyy-mm-dd")).format(m_date);
	}
	public int getState() {
		return m_state;
	}
	public void setState(int state) {
		m_state = state;
	}
	public int getPrice() {
		return m_price;
	}
	public void setPrice(int price) {
		m_price = price;
	}
	public String getStateString() {
		if(this.m_state == Order.ORDER_IS_PREPARING) {
			return "В ожидании";
		} else if(this.m_state == Order.ORDER_IS_SENT) {
			return "Отправлен";
		} else if(this.m_state == Order.ORDER_IS_COMPLETED) {
			return "Выполнен";
		} 
		return "";
	}
}
