package com.tianli.dbhelper;

import android.provider.BaseColumns;

public final class ShengjiContract {
	public static abstract class UserEntry implements BaseColumns {
		public static final String TABLE_NAME = "user";
		public static final String IP_COL = "ip";
		public static final String USER_NAME_COL = "username";
		public static final String SEAT_ID_COL = "seatid";

		public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME
				+ " (" + _ID + " INTEGER PRIMARY KEY, " + IP_COL
				+ " TEXT UNIQUE, " + USER_NAME_COL + " TEXT UNIQUE, "
				+ SEAT_ID_COL + " INTEGER)";
		public static final String SQL_DELETE = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;
	}

	public static abstract class SeatEntry implements BaseColumns {
		public static final String TABLE_NAME = "seat";
		public static final String SEAT_ID_COL = "seatid";
		public static final String READY_STATUS_COL = "readystatus";

		public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME
				+ " (" + _ID + " INTEGER PRIMARY KEY," + SEAT_ID_COL
				+ " INTEGER," + READY_STATUS_COL + " INTEGER" + " )";
		public static final String SQL_DELETE = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;
	}
}
