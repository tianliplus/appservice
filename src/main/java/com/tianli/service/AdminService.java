package com.tianli.service;

import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tianli.dbhelper.AdminDBHelper;
import com.tianli.dbhelper.SeatDBHelper;
import com.tianli.dbhelper.UserDBHelper;

public class AdminService extends BaseService {

	Context context;
	String tableName;
	public AdminService(Context context, String tableName) {
		this.context = context;
		this.tableName = tableName;
	}

	public AdminService(Context context, String tableName,
			HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
		this.context = context;
		this.tableName = tableName;
	}

	public AdminService(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}

	public boolean doReset(Context androidContext) {
		String[] tableNames = { UserDBHelper.TABLE_NAME };
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
		LinkedList<Map<String, String>> list = mDbHelper.select(db, col, sel,
				arg, group, having, order, limit);
		return list;
	}

	public void doInsert(String colString, String valString) {
		SQLiteDatabase db = null;
		if (tableName == SeatDBHelper.TABLE_NAME) {
			SeatDBHelper dbHelper = new SeatDBHelper(context);
			db = dbHelper.getWritableDatabase();
		}
		if (tableName == UserDBHelper.TABLE_NAME) {
			UserDBHelper dbHelper = new UserDBHelper(context);
			db = dbHelper.getWritableDatabase();
		}
		String[] cols = colString.split("\\.\\.");
		String[] vals = valString.split("\\.\\.");
		ContentValues values = new ContentValues();
		for (int i = 0; i < cols.length; i++) {
			values.put(cols[i], vals[i]);
		}
		db.insert(tableName, null, values);

	}
}
