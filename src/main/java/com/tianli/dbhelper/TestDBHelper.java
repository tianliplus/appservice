package com.tianli.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TestDBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "FeedReader.db";
	public static final int DATABASE_VERSION = 1;

	private static final String SQL_CREATE_ENTRIES = "CREATE TABLE testtable (id integer primary key, content text)";
	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS testtable";

	public TestDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);

	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}

	public void onDrop(SQLiteDatabase db) {
		db.execSQL(SQL_DELETE_ENTRIES);
	}

}
