package com.tianli.dbhelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tianli.dataobject.UserDO;

public class UserDBHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "shengji.db";
	public static final int DATABASE_VERSION = 1;

	public static final String TABLE_NAME = "user";

	public static final String ID_COL = "id";
	public static final String IP_COL = "ip";
	public static final String USER_NAME_COL = "username";
	public static final String SEAT_ID_COL = "seatid";

	private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ TABLE_NAME + " (" + ID_COL + " INTEGER PRIMARY KEY, " + IP_COL
			+ " TEXT UNIQUE, " + USER_NAME_COL + " TEXT UNIQUE, " + SEAT_ID_COL
			+ " INTEGER)";
	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
			+ TABLE_NAME;

	public UserDBHelper(Context context) {
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

	public void delete(SQLiteDatabase db, String selection, String[] args) {
		db.delete(TABLE_NAME, selection, args);
	}

	// select list
	public LinkedList<Map<String, String>> select(SQLiteDatabase db,
			String col, String sel, String arg, String group, String having,
			String order, String limit) {
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

	// select record
	public UserDO select(SQLiteDatabase db, String where) {
		UserDO userDo = new UserDO();
		String sql = "select * from " + TABLE_NAME;
		if (null != where && !where.equals("")) {
			sql += " where " + where;
		}
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			userDo.id = cursor.getInt(cursor.getColumnIndex(ID_COL));
			userDo.ip = cursor.getString(cursor.getColumnIndex(IP_COL));
			userDo.seatId = cursor.getInt(cursor.getColumnIndex(SEAT_ID_COL));
			userDo.userName = cursor.getString(cursor
					.getColumnIndex(USER_NAME_COL));
			return userDo;
		}
		// select failure
		userDo.id = -1;
		return userDo;
	}

	public int updateSeat(SQLiteDatabase db, UserDO userDo) {
		String[] arg = { userDo.ip };
		String[] cols = { UserDBHelper.SEAT_ID_COL, UserDBHelper.USER_NAME_COL };
		String[] vals = { "" + userDo.seatId, userDo.userName };
		return update(db, UserDBHelper.IP_COL + "=?", arg, cols, vals);

	}

	// update record
	public int update(SQLiteDatabase db, String selection, String[] args,
			String[] cols, String[] vals) {
		ContentValues values = new ContentValues();
		for (int i = 0; i < cols.length; i++) {
			values.put(cols[i], vals[i]);
		}
		int count = db.update(TABLE_NAME, values, selection, args);
		return count;
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
