package com.tianli.dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tianli.dbhelper.ShengjiContract.RoundStatusEntry;
import com.tianli.dbhelper.ShengjiContract.GameStatusEntry;
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
		// Delete all user EXCETP host
		String[] args = { "127.0.0.1" };
		db.delete(UserEntry.TABLE_NAME, "ip<>?", args);

		// Delete all seat status
		db.delete(SeatEntry.TABLE_NAME, null, null);
		// Insert all seat to status 0
		for (int i = 1; i <= 4; i++) {
			ContentValues seat = new ContentValues();
			seat.put(SeatEntry.SEAT_ID_COL, 1);
			seat.put(SeatEntry.READY_STATUS_COL, 0);
			db.insert(SeatEntry.TABLE_NAME, null, seat);
		}

		// Delete game status
		db.delete(GameStatusEntry.TABLE_NAME, null, null);
		// Reset game status
		ContentValues gameValues = new ContentValues();
		gameValues.put(GameStatusEntry.BANKER_SEAT_COL, 0);
		gameValues.put(GameStatusEntry.LEVEL_COL_1, 2);
		gameValues.put(GameStatusEntry.LEVEL_COL_2, 2);
		db.insert(GameStatusEntry.TABLE_NAME, null, gameValues);

		// Delete current status
		db.delete(RoundStatusEntry.TABLE_NAME, null, null);
		// Reset current status
		ContentValues currentValues = new ContentValues();
		currentValues.put(RoundStatusEntry.BANKER_SEAT_COL, 0);
		currentValues.put(RoundStatusEntry.ROUND_LEVEL_COL, 2);
		currentValues.put(RoundStatusEntry.SCORE_COL, 0);
		currentValues.put(RoundStatusEntry.MAIN_COLOR_COL, 0);
		currentValues.put(RoundStatusEntry.SHOW_MAIN_IP_COL, "");
		currentValues.put(RoundStatusEntry.SHOW_MAIN_CATEGORY, 0);
		db.insert(RoundStatusEntry.TABLE_NAME, null, currentValues);
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
