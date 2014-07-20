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
import com.tianli.dbhelper.ShengjiContract.UserEntry;
import com.tianli.result.LoginResult;
import com.tianli.service.SocketService;

/**
 * Servlet implementation class LoginServlet
 */
public class SeatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final int LOGIN_ACTION = 1;
	private static final int LOGOUT_ACTION = 2;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SeatServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * 
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LoginResult result = new LoginResult();

		// Obtain database object
		Context androidContext = (Context) getServletContext().getAttribute(
				"org.mortbay.ijetty.context");

		UserDAO userDAO = new UserDAO(androidContext);
		// Get user info
		UserDO userDo = new UserDO();
		userDo.userName = request.getParameter("username").trim();
		userDo.seatId = Integer.parseInt(request.getParameter("seatid"));
		userDo.ip = request.getRemoteAddr().trim();
		// Get user at seat info
		UserDO userAtSeatDo = userDAO.select(UserEntry.SEAT_ID_COL + "='"
				+ userDo.seatId + "'");
		// -----If the seat is empty-----
		if (userAtSeatDo.id == -1) {
			userDAO.updateSeat(userDo);
		} else {
			// -----If the seat is not empty-----
			// -----If it is himself, then he get up-----
			if (userDo.ip.equalsIgnoreCase(userAtSeatDo.ip)) {
				userDo.seatId = 0;
				userDAO.updateSeat(userDo);
			} else {
				// If it is someelse
				// return error
				result.actioncode = 4;
				result.rcode = -1;
			}
		}
		// Get seat status
		Map<Integer, String> seatStatus = userDAO.getSeatStatus();
		String message = parseSeatStatus(seatStatus);
		String[] clientsIp = userDAO.getClientsIp();
		// Broadcast to all clients
		SocketService service = new SocketService();
		service.sendGeneralSocket(SocketService.SEAT_CODE, clientsIp, message);

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
		doGet(request, response);
	}

}
