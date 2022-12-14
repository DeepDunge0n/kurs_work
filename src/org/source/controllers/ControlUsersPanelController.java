package org.source.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.fr2eman.send.DataResponse;
import org.source.ApplicationUI;
import org.source.models.Order;
import org.source.models.User;
import org.source.network.ServerFacade;
import org.source.service.CacheService;
import org.source.views.ClientDetailsPanel;
import org.source.views.base.BasePanel;

public class ControlUsersPanelController {
	
	private static ControlUsersPanelController instance = new ControlUsersPanelController();
	
	public static ControlUsersPanelController getInstance() {
		return instance;
	}
	
	public void search(BasePanel panel) {
		
		DataResponse response = null;
		try {
			response = ServerFacade.getInstance().requestGetAllUsers();
		} catch(ConnectException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), "Нет соединения с сервером",
					"Ошибка соединения", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(response.getError()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					response.getMessage());
			return;
		}
		CacheService.getInstance().clearListUsers();
		int numberProducts = Integer.valueOf(response.getData().get("number"));
		for(int i = 1; i <= numberProducts; i++) {
			int id = Integer.valueOf(response.getData().get("id_user" + i));
			String login = response.getData().get("login" + i);
			String f_name = response.getData().get("first_name" + i);
			String s_name = response.getData().get("second_name" + i);
			String m_name = response.getData().get("middle_name" + i);
			String email = response.getData().get("email" + i);
			CacheService.getInstance().addInListUsers(new User(
					id, login, f_name, s_name, m_name, email));
		}
		panel.updatePanel();
	}
	
	public void description() {
		
		DataResponse response = null;
		try {
			response = ServerFacade.getInstance().requestUserDetailsById();
		} catch(ConnectException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), "Нет соединения с сервером",
					"Ошибка соединения", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(response.getError()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					response.getMessage());
			return;
		} else {
			try {
				CacheService.getInstance().setId(Integer.valueOf(response.getData().get("id")));
			} catch(NumberFormatException e) {}
			CacheService.getInstance().setLogin(response.getData().get("login"));
			CacheService.getInstance().setSecondName(response.getData().get("second_name"));
			CacheService.getInstance().setFirstName(response.getData().get("first_name"));
			CacheService.getInstance().setMiddleName(response.getData().get("middle_name"));
			CacheService.getInstance().setEmail(response.getData().get("email"));
			CacheService.getInstance().setAccessUser(Integer.valueOf(response.getData().get("access")));
		}
		ApplicationUI.getInstance().push(new ClientDetailsPanel());
	}
	
	public void save() {
		if(CacheService.getInstance().getListUsers().isEmpty()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					"Список пустой.", 
					"Сохранение", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		try {	
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			Date date = new Date();
            String filename = "users[" + format.format(date) + "].xls" ;
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("orders");  

            HSSFRow rowhead = sheet.createRow((short)0);
            rowhead.createCell(0).setCellValue("id");
            rowhead.createCell(1).setCellValue("Логин");
            rowhead.createCell(2).setCellValue("Фамилия");
            rowhead.createCell(3).setCellValue("Имя");
            rowhead.createCell(4).setCellValue("Отчество");
            rowhead.createCell(5).setCellValue("Эл. почта");
            int index = 1;
            for(User user : CacheService.getInstance().getListUsers()) {
	            HSSFRow row = sheet.createRow((short)index);
	            row.createCell(0).setCellValue(String.valueOf(user.getId()));
	            row.createCell(1).setCellValue(user.getLogin());
	            row.createCell(2).setCellValue(user.getFirstName());
	            row.createCell(3).setCellValue(user.getSecondName());
	            row.createCell(4).setCellValue(user.getMiddleName());
	            row.createCell(5).setCellValue(user.getEmail());
	            ++index;
            }
            File logsDir = new File("logs");
            if(!logsDir.exists()) {
            	logsDir.mkdir();
            }
            FileOutputStream fileOut = new FileOutputStream("logs/" + filename);
            workbook.write(fileOut);
            fileOut.close();
            JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					"Пользователи сохранены в файл " + filename, 
					"Сохранение", JOptionPane.INFORMATION_MESSAGE);
        } catch ( Exception ex ) {
        	JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					"Файл не создан. Данные не сохранены.", 
					"Сохранение", JOptionPane.ERROR_MESSAGE);
        }
	}
	
	public void back() {
		ApplicationUI.getInstance().pop();
	}
	
}
