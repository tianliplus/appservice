package com.tianli.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InitDBHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "shengji.db";
	public static final int DATABASE_VERSION = 1;

	public static final String TABLE_NAME = "register";

	public InitDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
