package com.tianli.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketService {
	public int port = 8821;
	public final static String MSG_CODE = "0001";
	public final static String SEAT_CODE = "0002";

	public void sendMessage(String[] clientIp, String message) {
		String cmd = MSG_CODE + message;
		for (String ip : clientIp) {
			sendToIp(ip, cmd);
		}
	}

	public void sendGeneralSocket(String code, String[] clientIp, String message) {

	}

	private void sendToIp(String ip, String cmd) {
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
		byte[] bytes = cmd.getBytes();
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
