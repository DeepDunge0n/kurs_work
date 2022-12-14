package org.source.controllers;

import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.fr2eman.send.DataResponse;
import org.knowm.xchart.Chart;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.source.ApplicationUI;
import org.source.models.Order;
import org.source.models.Product;
import org.source.network.ServerFacade;
import org.source.service.CacheService;
import org.source.views.ControlOrderPanel;
import org.source.views.OrdersStatisticPanel;
import org.source.views.base.BasePanel;
import org.source.views.statistics.DiagrammCreator;
import org.source.views.statistics.GraphicCreator;

public class OrdersStatisticPanelController {
	
	private static OrdersStatisticPanelController instance = new OrdersStatisticPanelController();
	
	public static OrdersStatisticPanelController getInstance() {
		return instance;
	}
	
	private void getStatisticForYear(String year, int month) throws Exception {
		DataResponse response = null;
		try {
			response = ServerFacade.getInstance().requestStatisticOrdersForYear();
		} catch(ConnectException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), "Нет соединения с сервером",
					"Ошибка соединения", JOptionPane.ERROR_MESSAGE);
			throw new Exception();
		}
		if(response.getError()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					response.getMessage());
			throw new Exception();
		}
		List<Number> numberOrders = new ArrayList<Number>();
		for(int i = 1; i <= 12; ++i) {
			numberOrders.add(Integer.valueOf(response.getData().get("month" + i)));
		}
		CacheService.getInstance().setStatisticOrders(numberOrders);
	}
	
	private void getStatisticForMonth(String year, int month) throws Exception {
		DataResponse response = null;
		try {
			response = ServerFacade.getInstance().requestStatisticOrdersForMonth();
		} catch(ConnectException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), "Нет соединения с сервером",
					"Ошибка соединения", JOptionPane.ERROR_MESSAGE);
			throw new Exception();
		}
		if(response.getError()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					response.getMessage());
			throw new Exception();
		}
		int number = Integer.valueOf(response.getData().get("number"));
		List<Number> numberOrders = new ArrayList<Number>();
		for(int i = 1; i <= number; ++i) {
			numberOrders.add(Integer.valueOf(response.getData().get("day" + i)));
		}
		CacheService.getInstance().setStatisticOrders(numberOrders);
	}
	
	public void showGraphic(boolean isAllYear, String year, int month) {
		CacheService.getInstance().setIsStatisticForYear(isAllYear);
		try {
			try {
				CacheService.getInstance().setStatisticYear(Integer.valueOf(year));
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
						"Не выбран год");
				throw e;
			}
			if(isAllYear) {
				getStatisticForYear(year, month);
			} else {
				CacheService.getInstance().setStatisticMonth(month);
				getStatisticForMonth(year, month);
			}
		} catch(Exception e) {
			return;
		}
		GraphicCreator graphic = new GraphicCreator();
		graphic.showGraphic();
	}
	
	public void showDiagramm(boolean isAllYear, String year, int month) {
		CacheService.getInstance().setIsStatisticForYear(isAllYear);
		try {
			try {
				CacheService.getInstance().setStatisticYear(Integer.valueOf(year));
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
						"Не выбран год");
				throw e;
			}
			if(isAllYear) {
				getStatisticForYear(year, month);
			} else {
				CacheService.getInstance().setStatisticMonth(month);
				getStatisticForMonth(year, month);
			}
		} catch(Exception e) {
			return;
		}
		DiagrammCreator graphic = new DiagrammCreator();
		graphic.showGraphic();
	}
	
	public void back() {
		ApplicationUI.getInstance().pop();
	}
}
