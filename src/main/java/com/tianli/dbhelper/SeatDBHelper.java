package com.tianli.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SeatDBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "shengji.db";
	public static final int DATABASE_VERSION = 1;

	public static final String TABLE_NAME = "seat";

	public static final String SEAT_ID_COL = "seatid";
	public static final String READY_STATUS_COL = "readystatus";

	private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ TABLE_NAME + " (" + SEAT_ID_COL + " INTEGER PRIMARY KEY, "
			+ READY_STATUS_COL + " INTEGER)";
	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
			+ TABLE_NAME;

	public SeatDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}

	public void onDrop(SQLiteDatabase db) {
		db.execSQL(SQL_DELETE_ENTRIES);
	}

}
