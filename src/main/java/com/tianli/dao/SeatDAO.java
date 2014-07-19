package com.tianli.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tianli.dbhelper.SeatDBHelper;

public class SeatDAO {
	private Context androidContext;

	public SeatDAO(Context context) {
		androidContext = context;
	}

	public int setReadyStatus(int seatId, int status) {
		SeatDBHelper dbHelper = new SeatDBHelper(androidContext);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(SeatDBHelper.READY_STATUS_COL, status);
		String[] arg = { "" + seatId };
		return db.update(SeatDBHelper.TABLE_NAME, values,
				SeatDBHelper.SEAT_ID_COL + "=?", arg);
	}

	public int getUnreadyCount() {
		SeatDBHelper dbHelper = new SeatDBHelper(androidContext);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String[] cols = { "count(*)" };
		String where = SeatDBHelper.READY_STATUS_COL + "=?";
		String[] arg = { "1" };
		Cursor cursor = db.query(SeatDBHelper.TABLE_NAME, cols, where, arg,
				null, null, null);
		int count = cursor.getInt(0);
		return count;
	}
}
