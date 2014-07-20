package com.tianli.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.content.Context;

import com.google.gson.Gson;
import com.tianli.dao.UserDAO;
import com.tianli.dataobject.UserDO;
import com.tianli.result.BaseResult;
import com.tianli.service.SocketService;

/**
 * Servlet implementation class InitServlet
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InitServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String seat = request.getParameter("seat");
		if (null != seat && seat.equals("1")) {
			Context androidContext = (Context) getServletContext()
					.getAttribute("org.mortbay.ijetty.context");
			UserDAO userDAO = new UserDAO(androidContext);
			Map<Integer, String> seatStatus = userDAO.getSeatStatus();
			String message = parseSeatStatus(seatStatus);
			String[] clientsIp = { request.getRemoteAddr().trim() };
			// Broadcast to all clients
			SocketService.sendGeneralSocket(SocketService.SEAT_CODE, clientsIp,
					message);
			return;
		}
		BaseResult result = new BaseResult();
		String userName = request.getParameter("username");
		if (userName != null) {
			UserDO userDo = new UserDO();
			userDo.userName = userName.trim();
			userDo.seatId = 0;
			userDo.ip = request.getRemoteAddr().trim();
			Context androidContext = (Context) getServletContext()
					.getAttribute("org.mortbay.ijetty.context");
			UserDAO userDAO = new UserDAO(androidContext);

			userDAO.insert(userDo);

			result.rcode = 1;
		}
		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		out.println(gson.toJson(result));
		return;
	}

	private String parseSeatStatus(Map<Integer, String> seatStatus) {
		StringBuilder sb = new StringBuilder();
		// SeatID from 1 to 4, add the username
		for (int i = 1; i <= 4; i++) {
			Object user = seatStatus.get(i);
			if (null != user) {
				sb.append(i);
				sb.append(user);
				sb.append(',');
			}
		}
		return sb.toString();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
