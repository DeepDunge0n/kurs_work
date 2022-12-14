package org.source.controllers;

import java.net.ConnectException;

import javax.swing.JOptionPane;

import org.source.ApplicationUI;
import org.source.models.User;
import org.source.network.ServerFacade;
import org.source.service.CacheService;
import org.source.service.DataService;
import org.source.views.AuthorizationClientPanel;
import org.source.views.ControlClientPanel;
import org.source.views.RegistrationClientPanel;

import org.fr2eman.send.DataResponse;

public class AuthorizationClientPanelController {
	
	private String m_reason;
	
	private static AuthorizationClientPanelController instance = new AuthorizationClientPanelController();
	
	public static AuthorizationClientPanelController getInstance() {
		return instance;
	}
	private AuthorizationClientPanelController() {
		m_reason = "";
	}
	public void registration() {
		ApplicationUI.getInstance().push(new RegistrationClientPanel());
	}
	
	public void authorization(AuthorizationClientPanel panel) {
		
		if(!validation()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
			m_reason);
			return;
		}
		
		CacheService.getInstance().setAccessUser(User.ACCESS_CLIENT);
		DataResponse data = null;

		try {
			data = ServerFacade.getInstance().requestAuthorization();
		} catch(ConnectException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), "Нет соединения с сервером",
					"Ошибка соединения", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if(data.getError()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), data.getMessage(),
			"Ошибка", JOptionPane.ERROR_MESSAGE);
		} else {
			DataService.getInstance().setLoginCurrentUser(
					CacheService.getInstance().getLogin());
			DataService.getInstance().setPassCurrentUser(
					CacheService.getInstance().getPassword());
			ApplicationUI.getInstance().pop();
			ApplicationUI.getInstance().pop();
			ApplicationUI.getInstance().push(new ControlClientPanel());
			ApplicationUI.getInstance().validate();
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
			"Авторизация прошла успешно:)");
		}	
	}
	
	private boolean validation() {
		if(CacheService.getInstance().getLogin().isEmpty()) {
			m_reason = "Не введён логин";
			return false;
		}
		if(CacheService.getInstance().getPassword().isEmpty()) {
			m_reason = "Не введён пароль";
			return false;
		}
		return true;
	}
	
	public void back() {
		ApplicationUI.getInstance().pop();
	}
}
