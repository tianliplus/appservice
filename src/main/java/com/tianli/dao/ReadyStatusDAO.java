package com.tianli.dao;

import java.util.LinkedList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tianli.dbhelper.ShengjiContract.UserEntry;
import com.tianli.dbhelper.ShengjiDbHelper;

public class ReadyStatusDAO {
	private ShengjiDbHelper dbHelper;

	public ReadyStatusDAO(Context context) {
		dbHelper = new ShengjiDbHelper(context);
	}

	public String[] getTableClientsIp() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String[] cols = { UserEntry.IP_COL };
		String[] vals = { "0" };
		Cursor cursor = db.query(UserEntry.TABLE_NAME, cols,
				UserEntry.SEAT_ID_COL + ">?", vals, null, null, null);
		LinkedList<String> ipList = new LinkedList<String>();
		if (cursor.moveToFirst()) {
			ipList.add(cursor.getString(0));
		}
		while (cursor.moveToNext()) {
			ipList.add(cursor.getString(0));
		}
		return ipList.toArray(new String[0]);
	}
}
