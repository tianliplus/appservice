package com.tianli.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.tianli.dao.UserDAO;
import com.tianli.dataobject.UserDO;
import com.tianli.dbhelper.UserDBHelper;
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
		UserDBHelper mDbHelper = new UserDBHelper(androidContext);
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		// Get user info
		UserDO userDo = new UserDO();
		userDo.userName = request.getParameter("username").trim();
		userDo.seatId = Integer.parseInt(request.getParameter("seatid"));
		userDo.ip = request.getRemoteAddr().trim();
		// Get user at seat info
		UserDO userAtSeatDo = mDbHelper.select(db, UserDBHelper.SEAT_ID_COL
				+ "='" + userDo.seatId + "'");
		String[] args = { userDo.ip };
		// -----If the seat is empty-----
		if (userAtSeatDo.id == -1) {
			// -----Check if previous logged-----
			UserDO preLogin = mDbHelper.select(db, UserDBHelper.IP_COL + "='"
					+ userDo.ip + "'");
			if (preLogin.id != -1) {
				// If previous logged in.
				// Delete the previous record of the user
				mDbHelper.delete(db, UserDBHelper.IP_COL + " = ?", args);
				result.actioncode = 2;
				result.oldseat = preLogin.seatId;
			} else {
				result.actioncode = 1;
			}
			// Put the user into seatid
			mDbHelper.insert(db, userDo);
			result.rcode = 1;
			result.seatid = userDo.seatId;
		} else {
			// -----If the seat is not empty-----
			// -----If it is himself, then he get up-----
			if (userDo.ip.equalsIgnoreCase(userAtSeatDo.ip)) {
				mDbHelper.delete(db, UserDBHelper.IP_COL + "=?", args);
				result.rcode = 1;
				result.actioncode = 3;
				result.seatid = userDo.seatId;
			} else {
				// If it is someelse
				// return error
				result.actioncode = 4;
				result.rcode = -1;
			}
		}
		// Get seat status
		UserDAO userDAO = new UserDAO();
		Map<Integer, String> seatStatus = userDAO.getSeatStatus(androidContext);
		String message = parseSeatStatus(seatStatus);
		String[] clientsIp = userDAO.getClientsIp(androidContext);
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
