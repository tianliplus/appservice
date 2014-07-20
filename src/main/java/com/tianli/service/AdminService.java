package com.tianli.service;

import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.content.Context;

import com.tianli.dao.AdminDAO;
import com.tianli.result.AdminDbResult;

public class AdminService extends BaseService {

	Context androidContext;
	String tableName;

	public AdminService(Context context, String tableName) {
		this.androidContext = context;
		this.tableName = tableName;
	}

	public AdminService(Context context, String tableName,
			HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
		this.androidContext = context;
		this.tableName = tableName;
	}

	public AdminService(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}

	public AdminDbResult doReset() {
		AdminDbResult result = new AdminDbResult();
		try {
			AdminDAO adminDAO = new AdminDAO(androidContext);
			adminDAO.clearData();
			result.rcode = 1;
			result.adminresult = "All data cleared.";
		} catch (Exception e) {
			result.rcode = -1;
			result.message = "Error: " + e.getMessage();
		}
		return result;
	}

	public AdminDbResult doSelect(String tableName) {
		AdminDbResult result = new AdminDbResult();
		try {
			AdminDAO adminDAO = new AdminDAO(androidContext);
			String col = req.getParameter("col");
			String sel = req.getParameter("sel");
			String arg = req.getParameter("arg");
			String group = req.getParameter("group");
			String having = req.getParameter("having");
			String order = req.getParameter("order");
			String limit = req.getParameter("limit");
			LinkedList<Map<String, String>> list = adminDAO.select(tableName,
					col, sel, arg, group, having, order, limit);
			result.rcode = 1;
			result.adminresult = (list.size() != 0) ? list : "Empty table.";
		} catch (Exception e) {
			result.rcode = -1;
			result.message = "Error: " + e.getMessage();
		}
		return result;

	}

	public AdminDbResult doInsert(String tableName) {
		AdminDbResult result = new AdminDbResult();

		try {
			AdminDAO adminDAO = new AdminDAO(androidContext);

			String colString = req.getParameter("cols");
			String valString = req.getParameter("vals");
			String[] cols = colString.split("\\.\\.");
			String[] vals = valString.split("\\.\\.");

			long idx = adminDAO.insert(tableName, cols, vals);
			result.rcode = 1;
			result.adminresult = "Item inserted: Table " + tableName
					+ ", index " + idx;

		} catch (Exception e) {
			result.rcode = -1;
			result.message = "Error: " + e.getMessage();
		}
		return result;
	}
}
