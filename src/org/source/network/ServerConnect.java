package org.source.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.fr2eman.send.*;
import org.source.service.DataService;

public class ServerConnect {
	
	Socket m_serverSocket;
	
	public ServerConnect() {
		
	}
	
	boolean connectToServer() throws ConnectException {
		try {
			m_serverSocket = new Socket(DataService.getInstance().getNameServer(),
					DataService.getInstance().getPort());
		} catch (ConnectException e) {
			throw e;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	void sendRequest(DataRequest request) {
		ObjectOutputStream outputStreem = null;
		try {
			outputStreem = new ObjectOutputStream(m_serverSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		System.out.println("Sending Message");
		try {
			outputStreem.writeObject(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	DataResponse getResponse() {
		ObjectInputStream inputStreem = null;
		try {
			inputStreem = new ObjectInputStream(m_serverSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		DataResponse response = null;
		try {
			try {
				response = (DataResponse)inputStreem.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return response;
	}
	
	void closeConnect() {
		try {
			m_serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
