package com.tianli.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.tianli.dbhelper.AdminDBHelper;
import com.tianli.result.AdminDbResult;

public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminDbResult res = new AdminDbResult();
		// Get table name...
		String tableName = request.getParameter("table");
		// Get action type:
		// 0-rawSql with no return, 1-select, 2-insert, 3-update, 4-delete
		int actionType = Integer.parseInt(request.getParameter("action"));
		Context androidContext = (Context) getServletContext().getAttribute(
				"org.mortbay.ijetty.context");
		AdminDBHelper mDbHelper = new AdminDBHelper(androidContext, tableName);
		SQLiteDatabase db;
		// Get request parameters
		String col = request.getParameter("col");
		String sel = request.getParameter("sel");
		String arg = request.getParameter("arg");
		String group = request.getParameter("group");
		String having = request.getParameter("having");
		String order = request.getParameter("order");
		String limit = request.getParameter("limit");

		switch (actionType) {
		case 1:
			// -----Selection-----
			db = mDbHelper.getReadableDatabase();
			LinkedList<Map<String, String>> list = mDbHelper.select(db, col,
					sel, arg, group, having, order, limit);
			res.rcode = 1;
			res.adminres = list;
			break;

		default:
			break;
		}
		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		out.println(gson.toJson(res));
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
