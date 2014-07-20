package com.tianli.dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tianli.dbhelper.ShengjiContract.SeatEntry;
import com.tianli.dbhelper.ShengjiContract.UserEntry;
import com.tianli.dbhelper.ShengjiDbHelper;

public class AdminDAO {

	private ShengjiDbHelper dbHelper;

	public AdminDAO(Context context) {
		dbHelper = new ShengjiDbHelper(context);
	}

	public void clearData() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		// Delete all user
		String[] args = { "127.0.0.1" };
		db.delete(UserEntry.TABLE_NAME, "ip<>?", args);
		// Set all seat to status 0
		ContentValues values = new ContentValues();
		values.put(SeatEntry.READY_STATUS_COL, 0);
		db.update(SeatEntry.TABLE_NAME, values, null, null);
	}

	public LinkedList<Map<String, String>> select(String tableName, String col,
			String sel, String arg, String group, String having, String order,
			String limit) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String[] columns = null;
		String[] args = null;
		if (null != col) {
			columns = col.split("\\.\\.");
		}
		if (null != arg) {
			args = arg.split("\\.\\.");
		}
		Cursor c = db.query(tableName, columns, sel, args, group, having,
				order, limit);
		String[] colNames = c.getColumnNames();
		LinkedList<Map<String, String>> list = new LinkedList<Map<String, String>>();
		Map<String, String> map;

		if (c.moveToFirst()) {
			map = new HashMap<String, String>();
			for (String colName : colNames) {
				map.put(colName, c.getString(c.getColumnIndex(colName)));
			}
			list.add(map);
		}
		while (c.moveToNext()) {
			map = new HashMap<String, String>();
			for (String colName : colNames) {
				map.put(colName, c.getString(c.getColumnIndex(colName)));
			}
			list.add(map);
		}
		return list;
	}

	public long insert(String tableName, String[] cols, String[] vals) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		for (int i = 0; i < cols.length; i++) {
			values.put(cols[i], vals[i]);
		}
		return db.insert(tableName, null, values);
	}
}
