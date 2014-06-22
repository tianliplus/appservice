package com.tianli.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tianli.dataobject.UserDO;

public class LoginDBHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "shengji.db";
	public static final int DATABASE_VERSION = 1;

	private static final String TABLE_NAME = "user";

	private static final String ID_COL = "id";
	private static final String IP_COL = "ip";
	private static final String USER_NAME_COL = "username";
	private static final String SEAT_ID_COL = "seatid";

	private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ TABLE_NAME + " (" + ID_COL + " INTEGER PRIMARY KEY, " + IP_COL
			+ " TEXT UNIQUE, " + USER_NAME_COL + " TEXT UNIQUE, " + SEAT_ID_COL
			+ " INTEGER)";
	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
			+ TABLE_NAME;

	public LoginDBHelper(Context context) {
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

	public long insert(SQLiteDatabase db, UserDO userDo) {
		if (getCount(db, IP_COL + "='" + userDo.ip.trim() + "'") > 0)
			return -1;
		if (getCount(db, USER_NAME_COL + "='" + userDo.userName.trim() + "'") > 0)
			return -2;
		if (getCount(db, SEAT_ID_COL + "=" + userDo.seatId) > 0)
			return -3;
		ContentValues values = new ContentValues();
		values.put(IP_COL, userDo.ip);
		values.put(USER_NAME_COL, userDo.userName);
		values.put(SEAT_ID_COL, userDo.seatId);
		return db.insert(TABLE_NAME, null, values);
	}

	public int getCount(SQLiteDatabase db, String where) {
		String sql = "select count(*) from " + TABLE_NAME;
		if (null != where && !where.equals("")) {
			sql += " where " + where;
		}
		Cursor c = db.rawQuery(sql, null);
		if (c.moveToFirst()) {
			int count = c.getInt(0);
			c.close();
			return count;
		}
		c.close();
		return 0;
	}

}
