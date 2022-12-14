package org.source.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import org.fr2eman.send.DataResponse;
import org.source.ApplicationUI;
import org.source.models.Product;
import org.source.network.ServerFacade;
import org.source.service.CacheService;
import org.source.views.base.BasePanel;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ProductPanelController {
	
	private static ProductPanelController instance = new ProductPanelController();
	
	public static ProductPanelController getInstance() {
		return instance;
	}
	
	public void search(BasePanel panel) {
		
		DataResponse response = null;
		try {
			response = ServerFacade.getInstance().requestProducts();
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
		CacheService.getInstance().clearProducts();
		int numberProducts = Integer.valueOf(response.getData().get("number"));
		for(int i = 1; i <= numberProducts; i++) {
			int id = Integer.valueOf(response.getData().get("id_product" + i));
			String name = response.getData().get("name_product" + i);
			int number = Integer.valueOf(response.getData().get("number_product" + i));
			int price = Integer.valueOf(response.getData().get("price_product" + i));
			CacheService.getInstance().addProductsList(new Product(
					id, name, price, number));
		}
		panel.updatePanel();
	}
	
	public void description() {
		
		DataResponse response =null;
		try {
			response = ServerFacade.getInstance().requestDescriptionProduct();
		} catch(ConnectException e) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), "Нет соединения с сервером",
					"Ошибка соединения", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(response.getError()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					response.getMessage());
		} else {
			String description = "<html> Описание товара: ";
			String []substringDescription = response.getData().get("description").split(" ");
			int index = 55;
			for(String item : substringDescription) {
				description += item + " ";
				if(description.length() > index) {
					description += "<br>";
					index += 55;
				}
			}
			description += "</html>";
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					description, "Описание товара", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void save() {
		if(CacheService.getInstance().getListProducts().isEmpty()) {
			JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					"Список пустой.", 
					"Сохранение", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		try {	
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			Date date = new Date();
            String filename = "products[" + format.format(date) + "].xls" ;
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("products");  

            HSSFRow rowhead = sheet.createRow((short)0);
            rowhead.createCell(0).setCellValue("id");
            rowhead.createCell(1).setCellValue("Наименование");
            rowhead.createCell(2).setCellValue("Цена(в бел. руб.)");
            int index = 1;
            for(Product product : CacheService.getInstance().getListProducts()) {
	            HSSFRow row = sheet.createRow((short)index);
	            row.createCell(0).setCellValue(String.valueOf(product.getId()));
	            row.createCell(1).setCellValue(product.getName());
	            row.createCell(2).setCellValue(String.valueOf(product.getPrice()));
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
					"Товары сохранены в файл " + filename, 
					"Сохранение", JOptionPane.INFORMATION_MESSAGE);
        } catch ( Exception ex ) {
        	JOptionPane.showMessageDialog(ApplicationUI.getInstance(), 
					"Файл не создан. Товары не сохранены.", 
					"Сохранение", JOptionPane.ERROR_MESSAGE);
        }
	}
	
	public void back() {
		ApplicationUI.getInstance().pop();
	}
}
