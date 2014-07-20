package com.tianli.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.content.Context;

import com.google.gson.Gson;
import com.tianli.dao.UserDAO;
import com.tianli.result.BaseResult;
import com.tianli.service.SocketService;

/**
 * Servlet implementation class TextServlet
 */
public class TextServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TextServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		BaseResult baseResult = new BaseResult();
		try {
			Context androidContext = (Context) getServletContext()
					.getAttribute("org.mortbay.ijetty.context");

			UserDAO userDAO = new UserDAO(androidContext);

			String userName = request.getParameter("username");
			String text = request.getParameter("text");
			// get logged user ip
			String[] clientsIp = userDAO.getClientsIp();
			// build text
			String message = userName + ',' + text;
			// send by socket
			SocketService service = new SocketService();
			service.sendMessage(clientsIp, message);
		} catch (Exception e) {
			baseResult.message = e.getMessage();
		}
		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		out.println(gson.toJson(baseResult));
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
