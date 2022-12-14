package org.source.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.source.service.DataService;

public class Basket {
	
	private Map<Integer, Integer> m_dataOrder;
	
	public Basket() {
		m_dataOrder = new HashMap<Integer, Integer>();
	}
	
	public Basket(Map<Integer, Integer> data) {
		m_dataOrder = new HashMap<Integer, Integer>(data);
	}
	
	public Map<Integer, Integer> getDataOrder() {
		return this.m_dataOrder;
	}
	
	public void setDataOrder(Map<Integer, Integer> data) {
		m_dataOrder.clear();
		m_dataOrder.putAll(data);
	}
	
	public void addProduct(int idProduct, int numberProduct) {
		for(Entry<Integer, Integer> entry : DataService.getInstance().getBasket().getDataOrder().entrySet()) {
			if(entry.getKey() == idProduct) {
				m_dataOrder.put(idProduct, entry.getValue() + numberProduct);
				return;
			}
		}
		m_dataOrder.put(idProduct, numberProduct);
	}
	
	public void clearBasket() {
		m_dataOrder.clear();
	}
}
