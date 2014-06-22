package com.tianli.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.tianli.dataobject.UserDO;
import com.tianli.dbhelper.LoginDBHelper;
import com.tianli.result.LoginResult;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final int LOGIN_ACTION = 1;
	private static final int LOGOUT_ACTION = 2;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
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
		LoginDBHelper mDbHelper = new LoginDBHelper(androidContext);
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		// Get user info
		UserDO userDo = new UserDO();
		userDo.userName = request.getParameter("username").trim();
		userDo.seatId = Integer.parseInt(request.getParameter("seatid"));
		userDo.ip = request.getRemoteAddr().trim();
		// Get user at seat info
		UserDO userAtSeatDo = mDbHelper.select(db, LoginDBHelper.SEAT_ID_COL
				+ "=" + userDo.seatId);
		String[] args = { userDo.userName };
		// -----If the seat is empty-----
		if (userAtSeatDo.id == -1) {
			// -----Check if previous logged-----
			UserDO preLogin = mDbHelper.select(db, LoginDBHelper.USER_NAME_COL
					+ "=" + userDo.userName);
			if (preLogin.id != -1) {
				// If previous logged in.
				// Delete the previous record of the user
				mDbHelper
						.delete(db, LoginDBHelper.USER_NAME_COL + " = ?", args);
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
			if (userDo.userName == userAtSeatDo.userName) {
				mDbHelper.delete(db, LoginDBHelper.USER_NAME_COL + "=?", args);
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
		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		out.println(gson.toJson(result));
		return;
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
