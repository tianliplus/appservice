package com.tianli.dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tianli.dataobject.UserDO;
import com.tianli.dbhelper.ShengjiContract.UserEntry;
import com.tianli.dbhelper.ShengjiDbHelper;

public class UserDAO {

	private Context androidContext;
	private final String tableName = UserEntry.TABLE_NAME;


	public UserDAO(Context context) {
		androidContext = context;
	}

	public String[] getClientsIp() {
		ShengjiDbHelper dbHelper = new ShengjiDbHelper(androidContext);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		LinkedList<Map<String, String>> list = dbHelper.select(db, tableName,
				UserEntry.IP_COL, null, null, null, null, null, null);
		if (list.size() > 0) {
			Map<String, String>[] res = list.toArray(new Map[0]);
			String[] ips = new String[res.length];
			for (int i = 0; i < ips.length; i++) {
				ips[i] = res[i].get("ip");
			}
			return ips;
		} else {
			return null;
		}
	}

	public HashMap<Integer, String> getSeatStatus() {
		HashMap<Integer, String> result = new HashMap<Integer, String>();
		ShengjiDbHelper dbHelper = new ShengjiDbHelper(androidContext);
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		LinkedList<Map<String, String>> list = dbHelper.select(db, tableName,
				UserEntry.USER_NAME_COL + ".." + UserEntry.SEAT_ID_COL,
				UserEntry.SEAT_ID_COL + "> ?", "0", null, null, null, null);
		for (Map<String, String> item : list) {
			int seatID = Integer.valueOf(item.get(UserEntry.SEAT_ID_COL));
			String userName = item.get(UserEntry.USER_NAME_COL);
			result.put(seatID, userName);
		}
		return result;
	}

	public int getSeatByIp(String ip) {
		UserDO userDO = select(UserEntry.IP_COL + "='" + ip + "'");
		return userDO.seatId;
	}

	public UserDO select(String where) {
		ShengjiDbHelper dbHelper = new ShengjiDbHelper(androidContext);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		UserDO userDo = new UserDO();
		String sql = "select * from " + tableName;
		if (null != where && !where.equals("")) {
			sql += " where " + where;
		}
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			userDo.id = cursor.getInt(cursor.getColumnIndex(UserEntry._ID));
			userDo.ip = cursor.getString(cursor
					.getColumnIndex(UserEntry.IP_COL));
			userDo.seatId = cursor.getInt(cursor
					.getColumnIndex(UserEntry.SEAT_ID_COL));
			userDo.userName = cursor.getString(cursor
					.getColumnIndex(UserEntry.USER_NAME_COL));
			return userDo;
		}
		// select failure
		userDo.id = -1;
		return userDo;
	}

	public long insert(UserDO userDo) {
		ShengjiDbHelper dbHelper = new ShengjiDbHelper(androidContext);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (getCount(db, UserEntry.IP_COL + "='" + userDo.ip.trim() + "'") > 0)
			return -1;
		if (getCount(db,
				UserEntry.USER_NAME_COL + "='" + userDo.userName.trim() + "'") > 0)
			return -2;
		if (getCount(db, UserEntry.SEAT_ID_COL + "=" + userDo.seatId) > 0)
			return -3;
		ContentValues values = new ContentValues();
		values.put(UserEntry.IP_COL, userDo.ip);
		values.put(UserEntry.USER_NAME_COL, userDo.userName);
		values.put(UserEntry.SEAT_ID_COL, userDo.seatId);
		return db.insert(tableName, null, values);
	}

	private int getCount(SQLiteDatabase db, String where) {
		String sql = "select count(*) from " + tableName;
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

	public void updateSeat(UserDO userDO) {
		ShengjiDbHelper dbHelper = new ShengjiDbHelper(androidContext);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		String selection = UserEntry.IP_COL + "=?";
		String[] args = { userDO.ip };
		String[] cols = { UserEntry.SEAT_ID_COL };
		String[] vals = { "" + userDO.seatId };
		update(db, selection, args, cols, vals);
	}

	private int update(SQLiteDatabase db, String selection, String[] args,
			String[] cols, String[] vals) {
		ContentValues values = new ContentValues();
		for (int i = 0; i < cols.length; i++) {
			values.put(cols[i], vals[i]);
		}
		int count = db.update(tableName, values, selection, args);
		return count;
	}
}
