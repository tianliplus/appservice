package com.tianli.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.content.Context;

import com.google.gson.Gson;
import com.tianli.dao.SeatDAO;
import com.tianli.dao.UserDAO;
import com.tianli.result.BaseResult;

/**
 * Servlet implementation class ReadyStatusServlet
 */
public class ReadyStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReadyStatusServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		BaseResult result = new BaseResult();

		String status = request.getParameter("status");
		if (status == null || "".equals(status)) {
			result.rcode = -1;
			Gson gson = new Gson();
			PrintWriter writer = response.getWriter();
			writer.println(gson.toJson(result));
			return;
		}
		Context androidContext = (Context) getServletContext().getAttribute(
				"org.mortbay.ijetty.context");
		UserDAO userDAO = new UserDAO();
		SeatDAO seatDAO = new SeatDAO(androidContext);
		// Get client ip
		String ip = request.getRemoteAddr().trim();
		// Get seatId by ip
		int seatId = userDAO.getSeatByIp(androidContext, ip);
		// Set ready status
		seatDAO.setReadyStatus(seatId, Integer.valueOf(status));
		// Notify client
		result.rcode = 1;
		Gson gson = new Gson();
		PrintWriter writer = response.getWriter();
		writer.println(gson.toJson(result));
		// Respond to client status changed
		// Check count of ready=0, if 0, start game
		int countUnready = seatDAO.getUnreadyCount();
		if (countUnready == 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Notify client to change to gaming status

			// Get game status (banker/level12/level34)

			/*
			 * Reset round status
			 * (maincolor/banker/currentlevel/score/bottomcards)
			 */

			// Tell banker to ask for dealing
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
