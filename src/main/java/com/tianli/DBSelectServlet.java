package com.tianli;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Servlet implementation class DBSelectServlet
 */
public class DBSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DBSelectServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Context androidContext = (Context) getServletContext().getAttribute(
				"org.mortbay.ijetty.context");
		TestDBHelper mDbHelper = new TestDBHelper(androidContext);
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		String[] projection = { "id", "content" };
		String sortOrder = "id ASC";
		Cursor cursor = db.query("testtable", projection, null, null, null,
				null, sortOrder);
		cursor.moveToFirst();
		PrintWriter out = response.getWriter();
		out.println("Work done!");
		while (cursor.moveToNext()) {
			out.println("id : " + cursor.getInt(0) + ", content : "
					+ cursor.getString(1));
		}
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
