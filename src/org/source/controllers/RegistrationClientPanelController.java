package org.source.controllers;

import java.net.ConnectException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.fr2eman.send.DataResponse;
import org.source.ApplicationUI;
import org.source.models.User;
import org.source.network.ServerFacade;
import org.source.service.CacheService;

public class RegistrationClientPanelController {
	
	private static final String m_regEmail = "^[_A-Za-z0-9-\\+]+(\\." + 
		"[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String m_regLogin = "^[a-zA-Z][a-zA-Z0-9-_\\.]{2,20}$";
	private static final String m_regPass = "^[a-zA-Z0-9-_\\.]{6,20}$";
	private static final String m_regNames = "^([а-яА-ЯёЁ])+|([a-zA-Z])+$";
	private String m_reason;
	
	private static RegistrationClientPanelController instance = new RegistrationClientPanelController();
	
	public static RegistrationClientPanelController getInstance() {
		return instance;
	}
	
	private RegistrationClientPanelController() {
		m_reason = "";
	}
	
	public void registration() {
		
		if(!validation()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
			m_reason);
			return;
		}
		
		if(!CacheService.getInstance().getPassword().equals(
				CacheService.getInstance().getRepetitionPass())) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
			"Не правильно введён повтор пароля");
			return;
		}
		
		CacheService.getInstance().setAccessUser(User.ACCESS_CLIENT);
		DataResponse data = null;
		try {
			data = ServerFacade.getInstance().requestRegistration();
		} catch(ConnectException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), "Нет соединения с сервером",
					"Ошибка соединения", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(data.getError()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
			data.getMessage());
			return;
		}
		Map<String, String> response = data.getData();
		
		if(Boolean.valueOf(response.get("registration_status"))) {
			ApplicationUI.getInstance().pop();
		}
		JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
				response.get("message"));
	}
	
	public void back() {
		ApplicationUI.getInstance().pop();
	}
	
	private boolean validation() {
		Pattern regEmail = Pattern.compile(m_regNames);
		Matcher matcher = regEmail.matcher(CacheService.getInstance().getSecondName());
		if(!matcher.matches()) {
			m_reason = "Фамилия введёна не корректно";
			return false;
		}
		regEmail = Pattern.compile(m_regNames);
		matcher = regEmail.matcher(CacheService.getInstance().getFirstName());
		if(!matcher.matches()) {
			m_reason = "Имя введёно не корректно";
			return false;
		}
		regEmail = Pattern.compile(m_regNames);
		matcher = regEmail.matcher(CacheService.getInstance().getMiddleName());
		if(!matcher.matches()) {
			m_reason = "Отчество введён не корректно";
			return false;
		}
		regEmail = Pattern.compile(m_regEmail);
		matcher = regEmail.matcher(CacheService.getInstance().getEmail());
		if(!matcher.matches()) {
			m_reason = "Эл. адресс введён не корректно";
			return false;
		}
		regEmail = Pattern.compile(m_regLogin);
		matcher = regEmail.matcher(CacheService.getInstance().getLogin());
		if(!matcher.matches()) {
			m_reason = "Логин введён не корректно(от 3 до 20)";
			return false;
		}
		regEmail = Pattern.compile(m_regPass);
		matcher = regEmail.matcher(CacheService.getInstance().getPassword());
		if(!matcher.matches()) {
			m_reason = "Пароль должен содержать только буквы и цифры(от 6 до 20)";
			return false;
		}
		m_reason = "";
		return true;
	}
}
