package com.tianli.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketService {
	public int port = 8821;

	public void sendMessage(String[] clientIp, String message) {
		for (String ip : clientIp) {
			sendToIp(ip, message);
		}
	}

	private void sendToIp(String ip, String message) {
		Socket socket = null;
		OutputStream outputStream = null;
		try {
			socket = new Socket(ip, 8821);
			outputStream = socket.getOutputStream();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] bytes = message.getBytes();
		try {
			outputStream.write(bytes);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (null != socket) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
