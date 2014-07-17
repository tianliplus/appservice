package com.tianli.dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tianli.dbhelper.UserDBHelper;

public class UserDAO {
	public String[] getClientsIp(Context context) {
		UserDBHelper mDbHelper = new UserDBHelper(context);
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		LinkedList<Map<String, String>> list = mDbHelper.select(db, "ip", null,
				null, null, null, null, null);
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

	public HashMap<Integer, String> getSeatStatus(Context context) {
		HashMap<Integer, String> result = new HashMap<Integer, String>();
		UserDBHelper mDbHelper = new UserDBHelper(context);
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		LinkedList<Map<String, String>> list = mDbHelper.select(db,
				"username..seatid", null, null, null, null, null, null);
		for(Map<String, String> item:list){
			int seatID=Integer.valueOf(item.get(UserDBHelper.SEAT_ID_COL));
			String userName=item.get(UserDBHelper.USER_NAME_COL);
			result.put(seatID, userName);
		}
		return result;
	}
}
