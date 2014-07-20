package com.tianli.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tianli.dbhelper.ShengjiContract.SeatEntry;
import com.tianli.dbhelper.ShengjiDbHelper;

public class SeatDAO {
	private Context androidContext;
	private final String tableName = SeatEntry.TABLE_NAME;

	public SeatDAO(Context context) {
		androidContext = context;
	}

	public int setReadyStatus(int seatId, int status) {
		ShengjiDbHelper dbHelper = new ShengjiDbHelper(androidContext);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(SeatEntry.READY_STATUS_COL, status);
		String[] arg = { "" + seatId };
		return db.update(tableName, values, SeatEntry.SEAT_ID_COL + "=?", arg);
	}

	public int getUnreadyCount() {
		ShengjiDbHelper dbHelper = new ShengjiDbHelper(androidContext);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String[] cols = { "count(*)" };
		String where = SeatEntry.READY_STATUS_COL + "=?";
		String[] arg = { "1" };
		Cursor cursor = db.query(tableName, cols, where, arg, null, null, null);
		int count = cursor.getInt(0);
		return count;
	}
}
