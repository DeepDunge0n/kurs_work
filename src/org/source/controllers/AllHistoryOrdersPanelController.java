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
import org.source.models.Product;
import org.source.network.ServerFacade;
import org.source.service.CacheService;
import org.source.views.ControlOrderPanel;
import org.source.views.OrdersStatisticPanel;
import org.source.views.base.BasePanel;

public class AllHistoryOrdersPanelController {
	
	private static AllHistoryOrdersPanelController instance = new AllHistoryOrdersPanelController();
	
	public static AllHistoryOrdersPanelController getInstance() {
		return instance;
	}
	
	public void search(BasePanel panel, Date beginDate, Date endDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		CacheService.getInstance().setFirstDate(
				beginDate != null ? dateFormat.format(beginDate) : "");
		CacheService.getInstance().setLastDate(
				endDate != null ? dateFormat.format(endDate) : "");
		CacheService.getInstance().clearListOrders();
		DataResponse data = null;
		try {
			data =  ServerFacade.getInstance().requestGetAllOrders();
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
		panel.updatePanel();
	}
	
	public void description() {
		DataResponse data = null;
		try {
			data = ServerFacade.getInstance().requestOrderDetails();
		} catch(ConnectException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), "Нет соединения с сервером",
					"Ошибка соединения", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(data.getError()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
			data.getMessage());
			return;
		} else {
			CacheService.getInstance().setCurrentOrder(new Order(
					Integer.valueOf(data.getData().get("id_order")), 
					Integer.valueOf(data.getData().get("user")),
					data.getData().get("date_order"), 
					Integer.valueOf(data.getData().get("state_order")),
					Integer.valueOf(data.getData().get("summ_order"))));
			CacheService.getInstance().setLogin(data.getData().get("login"));
			int numberOrders = Integer.valueOf(data.getData().get("number_products"));
			CacheService.getInstance().clearProducts();
			for(int i = 1; i <= numberOrders; ++i) {
				CacheService.getInstance().addProductsList(new Product(
						Integer.valueOf(data.getData().get("id_product" + i)),
						data.getData().get("name_product" + i),
						Integer.valueOf(data.getData().get("price_product" + i)),
						Integer.valueOf(data.getData().get("number_product" + i))));
			}
		}
		ApplicationUI.getInstance().push(new ControlOrderPanel());
	}
	
	public void save() {
		if(CacheService.getInstance().getListOrders().isEmpty()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					"Список пустой.", 
					"Сохранение", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		try {	
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			Date date = new Date();
            String filename = "orders[" + format.format(date) + "].xls" ;
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("orders");  

            HSSFRow rowhead = sheet.createRow((short)0);
            rowhead.createCell(0).setCellValue("№ Заказа");
            rowhead.createCell(1).setCellValue("Дата заказа");
            rowhead.createCell(2).setCellValue("Состояние");
            int index = 1;
            for(Order prders : CacheService.getInstance().getListOrders()) {
	            HSSFRow row = sheet.createRow((short)index);
	            row.createCell(0).setCellValue(String.valueOf(prders.getId()));
	            row.createCell(1).setCellValue(prders.getStringDate());
	            row.createCell(2).setCellValue(String.valueOf(prders.getStateString()));
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
					"Заказы сохранены в файл " + filename, 
					"Сохранение", JOptionPane.INFORMATION_MESSAGE);
        } catch ( Exception ex ) {
        	JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					"Файл не создан. Данные не сохранены.", 
					"Сохранение", JOptionPane.ERROR_MESSAGE);
        }
	}
	
	public void statistics() {
		ApplicationUI.getInstance().push(new OrdersStatisticPanel());
	}
	
	public void back() {
		CacheService.getInstance().clearListOrders();
		ApplicationUI.getInstance().pop();
	}
}
