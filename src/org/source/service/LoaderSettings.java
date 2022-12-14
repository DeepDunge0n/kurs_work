package org.source.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class LoaderSettings {

	private static final String SERVER_PORT = "SERVER_PORT";
	private static final String SERVER_NAME = "SERVER_NAME";
	
	private static LoaderSettings instance = new LoaderSettings();

	public static LoaderSettings getInstance() {
		return instance;
	}
	
	public void initConfigurations(String nameFileConfig) throws IOException {
		
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(nameFileConfig)));
		} catch (FileNotFoundException e) {
			System.out.println("Файл config.conf не найден.");
			throw e;
		} catch (IOException e) {
			System.out.println("Ошибка чтения файла config.conf");
			throw e;
		}
		boolean isValidProperties = true;
		String messageException = "";
		String serverName = properties.getProperty(SERVER_NAME);
		if(serverName == null || serverName.isEmpty()) {
			messageException = "В файле config.conf не указан адресс сервера";
			isValidProperties = false;
		} else DataService.getInstance().setNameServer(serverName);
		
		int serverPort = 0;
		try {
			serverPort = Integer.valueOf(properties.getProperty(SERVER_PORT));
			if(serverPort <= 1024 || serverPort > 65535) {
				messageException = "Порт сервера должен быть в диапахоне от 1025 до 65535";
				isValidProperties = false;
			} else DataService.getInstance().setPort(serverPort);
		} catch(NumberFormatException e) {
			messageException = "В файле config.conf не указан порт сервера, или указан некорректно";
			isValidProperties = false;
		}
		
		if(!isValidProperties) throw new IOException(messageException);
	}
	
}
