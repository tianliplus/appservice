package com.tianli.service;

import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tianli.dbhelper.AdminDBHelper;
import com.tianli.dbhelper.LoginDBHelper;

public class AdminService extends BaseService {

	public AdminService(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}

	public boolean doReset(Context androidContext) {
		String[] tableNames = { LoginDBHelper.TABLE_NAME };
		try {
		for (String tableName : tableNames) {
			AdminDBHelper mDbHelper = new AdminDBHelper(androidContext,
					tableName);
			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			mDbHelper.clearData(db);
		}
		return true;
		} catch (Exception e) {
			return false;
		}
	}

	public LinkedList<Map<String, String>> doSelect(Context androidContext,
			String tableName) {
		AdminDBHelper mDbHelper = new AdminDBHelper(androidContext, tableName);
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		// Get request parameters
		String col = req.getParameter("col");
		String sel = req.getParameter("sel");
		String arg = req.getParameter("arg");
		String group = req.getParameter("group");
		String having = req.getParameter("having");
		String order = req.getParameter("order");
		String limit = req.getParameter("limit");
		// Search
		try {
			LinkedList<Map<String, String>> list = mDbHelper.select(db, col,
					sel, arg, group, having, order, limit);
			return list;
		} catch (Exception e) {
			return null;
		}
	}
}
