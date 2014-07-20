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

	public static abstract class GameStatusEntry implements BaseColumns {
		public static final String TABLE_NAME = "gamestatus";

		public static final String BANKER_SEAT_COL = "bankerseat";
		public static final String LEVEL_COL_1 = "level1";
		public static final String LEVEL_COL_2 = "level2";

		public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME
				+ " (" + _ID + " INTEGER PRIMARY KEY," + BANKER_SEAT_COL
				+ " INTEGER," + LEVEL_COL_1 + " INTEGER," + LEVEL_COL_2
				+ " INTEGER" + " )";
		public static final String SQL_DELETE = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;
	}

	public static abstract class RoundStatusEntry implements BaseColumns {
		public static final String TABLE_NAME = "roundstatus";

		public static final String BANKER_SEAT_COL = "bankerseat";
		public static final String ROUND_LEVEL_COL = "level";
		public static final String SCORE_COL = "score";
		public static final String MAIN_COLOR_COL = "maincolor";
		public static final String SHOW_MAIN_IP_COL = "mainip";
		public static final String SHOW_MAIN_CATEGORY = "maincategory";

		public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME
				+ " (" + _ID + " INTEGER PRIMARY KEY," + BANKER_SEAT_COL
				+ " INTEGER," + ROUND_LEVEL_COL + " INTEGER," + SCORE_COL
				+ " INTEGER," + MAIN_COLOR_COL + " INTEGER," + SHOW_MAIN_IP_COL
				+ " TEXT," + SHOW_MAIN_CATEGORY + " INTEGER" + " )";
		public static final String SQL_DELETE = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;
	}
}
