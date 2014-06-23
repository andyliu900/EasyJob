package com.ideacode.easyjob.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLite���ݿ��������
 * @author ly-suhai
 *
 */
public class DBHelper extends SQLiteOpenHelper{
	// ���ݿ���
	private static final String DB_NAME = "easyjob.db";

	// ���ݿ�汾
	private static final int VERSION = 1;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		CompanyDataHelper.CompanyDBInfo.TABLE.create(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
