package org.source.controllers;

import java.net.ConnectException;

import javax.swing.JOptionPane;

import org.fr2eman.send.DataResponse;
import org.source.ApplicationUI;
import org.source.models.Order;
import org.source.network.ServerFacade;
import org.source.service.CacheService;
import org.source.views.HistoryOrdersPanel;

public class ClientPersonalPanelController {
	
	private static ClientPersonalPanelController instance = new ClientPersonalPanelController();
	
	public static ClientPersonalPanelController getInstance() {
		return instance;
	}

	public void openHistory() {
		
		CacheService.getInstance().setSearchString("");
		CacheService.getInstance().setFirstDate("");
		CacheService.getInstance().setLastDate("");
		
		DataResponse data = null;
		try {
			data = ServerFacade.getInstance().requestGetOrdersUser();
		} catch(ConnectException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), "Нет соединения с сервером",
					"Ошибка соединения", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(data.getError()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
			data.getMessage());
		} else {
			int numberOrders = Integer.valueOf(data.getData().get("number"));
			for(int i = 1; i <= numberOrders; ++i) {
				CacheService.getInstance().addInListOrders(new Order(
						Integer.valueOf(data.getData().get("id" + i)),
						Integer.valueOf(data.getData().get("user" + i)),
						data.getData().get("date" + i),
						Integer.valueOf(data.getData().get("state" + i))));
			}
		}
		
		ApplicationUI.getInstance().push(new HistoryOrdersPanel());
	}
	
	public void back() {
		ApplicationUI.getInstance().pop();
	}
}
