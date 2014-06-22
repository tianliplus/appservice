package com.tianli.dbhelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminDBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "shengji.db";
	public static final int DATABASE_VERSION = 1;

	private String TABLE_NAME = "";

	public AdminDBHelper(Context context, String tableName) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		TABLE_NAME = tableName;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
	}

	public LinkedList<Map<String, String>> select(SQLiteDatabase db,
			String col, String sel, String arg,
			String group, String having, String order, String limit) {

		String[] columns = null;
		String[] args = null;
		if (null != col) {
			columns = col.split("\\.\\.");
		}
		if (null != arg) {
			args = arg.split("\\.\\.");
		}
		Cursor c = db.query(TABLE_NAME, columns, sel, args, group, having,
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
}
