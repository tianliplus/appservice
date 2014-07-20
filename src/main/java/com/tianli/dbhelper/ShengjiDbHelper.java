package com.tianli.dbhelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tianli.dbhelper.ShengjiContract.SeatEntry;
import com.tianli.dbhelper.ShengjiContract.UserEntry;

public class ShengjiDbHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "shengji.db";
	public static final int DATABASE_VERSION = 1;

	public ShengjiDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(UserEntry.SQL_CREATE);
		db.execSQL(SeatEntry.SQL_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL(UserEntry.SQL_DELETE);
		db.execSQL(SeatEntry.SQL_DELETE);
		onCreate(db);
	}

	public LinkedList<Map<String, String>> select(SQLiteDatabase db,
			String tableName, String col, String sel, String arg, String group,
			String having, String order, String limit) {
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
}
