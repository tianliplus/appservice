package com.tianli.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketService {
	public final static int port = 8821;
	public final static String MSG_CODE = "0001";
	public final static String SEAT_CODE = "0002";
	public final static String START_GAME_CODE = "0003";

	public static void sendMessage(String[] clientIp, String message) {
		sendGeneralSocket(MSG_CODE, clientIp, message);
	}

	public static void sendGeneralSocket(String code, String[] clientIp,
			String message) {
		String cmd = code + message;

		for (String ip : clientIp) {
			sendToIp(ip, cmd);
		}
	}

	private static void sendToIp(String ip, String cmd) {
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
		} catch (Exception e) {
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
