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
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LoginResult result = new LoginResult();
		long rowId = 0;

		Context androidContext = (Context) getServletContext().getAttribute(
				"org.mortbay.ijetty.context");
		LoginDBHelper mDbHelper = new LoginDBHelper(androidContext);
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		switch (Integer.parseInt(request.getParameter("action"))) {
		case LOGIN_ACTION:
			if (mDbHelper.getCount(db, "") <= 4) {
				UserDO userDo = new UserDO();
				userDo.userName = request.getParameter("username").trim();
				userDo.seatId = Integer
						.parseInt(request.getParameter("seatid"));
				userDo.ip = request.getRemoteAddr().trim();
				rowId = mDbHelper.insert(db, userDo);
				if (rowId > 0) {
					result.rowId = (int) rowId;
					result.rcode = 1;
					result.seatId = Integer.parseInt(request
							.getParameter("seatid"));
				}
			}
			break;
		case LOGOUT_ACTION:

			break;
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
