package org.source.controllers;

import java.net.ConnectException;
import java.util.Map;

import javax.swing.JOptionPane;

import org.fr2eman.send.DataResponse;
import org.source.ApplicationUI;
import org.source.models.User;
import org.source.network.ServerFacade;
import org.source.service.CacheService;
import org.source.service.DataService;
import org.source.views.ControlAdminPanel;
import org.source.views.ControlPanel;

public class AuthorizationEmployeePanelController {
	
	private static AuthorizationEmployeePanelController instance = new AuthorizationEmployeePanelController();
	
	public static AuthorizationEmployeePanelController getInstance() {
		return instance;
	}

	public void authorization() {
		
		CacheService.getInstance().setAccessUser(User.ACCESS_EMPLOYEE);
		DataResponse data = null;
		try {
			data = ServerFacade.getInstance().requestAuthorization();
		} catch(ConnectException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), "Нет соединения с сервером",
					"Ошибка соединения", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(data.getError()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
			data.getMessage());
		} else {
			Map<String, String> response = data.getData();
			int access = Integer.valueOf(response.get("access"));
			ApplicationUI.getInstance().pop();
			ApplicationUI.getInstance().pop();
			if(access == User.ACCESS_ADMIN) {
				ApplicationUI.getInstance().push(new ControlAdminPanel());
				JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
				"Авторизация прошла успешно:), Получены права администратора!");
			} else {
				ApplicationUI.getInstance().push(new ControlPanel());
				JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
				"Сервер вернул непонятного пользователя");
				return;
			}
			DataService.getInstance().setLoginCurrentUser(
					CacheService.getInstance().getLogin());
			DataService.getInstance().setPassCurrentUser(
					CacheService.getInstance().getPassword());
			ApplicationUI.getInstance().validate();
		}
	}
	
	public void back() {
		ApplicationUI.getInstance().pop();
	}
}
