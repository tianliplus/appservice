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

import com.google.gson.Gson;
import com.tianli.result.AdminDbResult;
import com.tianli.service.AdminService;

public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AdminDbResult res = new AdminDbResult();
		// Check if admin
		if (!"127.0.0.1".equals(request.getRemoteAddr().trim())) {
			res.rcode = -1;
			res.message = "Permission Deny! IP: "
					+ request.getRemoteAddr().trim();
		} else {

			Context androidContext = (Context) getServletContext()
					.getAttribute("org.mortbay.ijetty.context");

			// Get action type:
			// 0-reset server, 1-select, 2-insert, 3-update, 4-delete,
			// 5-drop
			int actionType = Integer.parseInt(request.getParameter("action"));
			// Get table name...
			String tableName = request.getParameter("table");
			AdminService service = new AdminService(androidContext, tableName,
					request, response);
			switch (actionType) {
			case 0:
				// -----Reset Server-----
				if (service.doReset(androidContext)) {
					res.rcode = 1;
					res.adminresult = "All data cleared.";
				} else {
					res.rcode = -1;
					res.message = "Unknown error.";
				}
				break;
			case 1:
				// -----Selection-----
				try {
					// query
					LinkedList<Map<String, String>> list = service.doSelect(
							androidContext, tableName);
					res.rcode = 1;
					res.adminresult = (list.size() != 0) ? list
							: "Empty table.";
				} catch (Exception e) {
					res.rcode = -1;
					res.message = "Error: " + e.getMessage();
				}
				break;
			case 2:
				// -----Insertion-----
				String colString = request.getParameter("cols");
				String valString = request.getParameter("vals");
				service.doInsert(colString, valString);
			default:
				break;
			}
		}
		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		out.println(gson.toJson(res));
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
