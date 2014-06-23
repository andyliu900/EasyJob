package com.ideacode.easyjob.dao;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.ideacode.easyjob.EasyJobApplication;
import com.ideacode.easyjob.util.DebugLog;

/**
 * ContentProvider封装组件
 * @author ly-suhai
 *
 */
public class DataProvider extends ContentProvider {

	static final Object DBLock = new Object();

	public static final String AUTHORITY = "com.ideacode.easyjob.provider";

	public static final String SCHEME = "content://";

	public static final String PATH_COMPANIES = "/companies";

	public static final Uri COMPANIES_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_COMPANIES);

	private static final int COMPANIES = 0;

	/** http://www.eoeandroid.com/thread-248245-1-1.html  keyword：表的MIME类型们*/
	public static final String COMPANIES_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.ideacode.easyjob.company";

	private static final UriMatcher sUriMatcher;

	private static DBHelper mDBHelper;

	public static DBHelper getDBHelper() {
		if (mDBHelper == null) {
			mDBHelper = new DBHelper(EasyJobApplication.getContext());
		}
		return mDBHelper;
	}

	@Override
	public boolean onCreate() {
		return true;
	}

	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(AUTHORITY, "companies", COMPANIES);
	}

	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) {
		case COMPANIES:
			return COMPANIES_CONTENT_TYPE;
		default:
			DebugLog.e("IllegalArgumentException:Unknown URI" + uri);
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		synchronized (DBLock) {
			SQLiteDatabase db = getDBHelper().getWritableDatabase();

			int count = 0;
			String table = matchTable(uri);
			db.beginTransaction();
			try {
				count = db.delete(table, selection, selectionArgs);
				db.setTransactionSuccessful();
			} finally {
				db.endTransaction();
			}
			getContext().getContentResolver().notifyChange(uri, null);
			return count;
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		synchronized (DBLock) {
			String table = matchTable(uri);
			SQLiteDatabase db = getDBHelper().getWritableDatabase();
			long rowId = 0;
			db.beginTransaction();
			try {
				rowId = db.insert(table, null, values);
				db.setTransactionSuccessful();
			} catch (Exception e) {
				DebugLog.e(e.getMessage());
			} finally {
				db.endTransaction();
			}
			if (rowId > 0) {
				Uri returnUri = ContentUris.withAppendedId(uri, rowId);
				getContext().getContentResolver().notifyChange(uri, null);
				return returnUri;
			}
			throw new SQLException("Failed to insert row into " + uri);
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		synchronized (DBLock) {
			SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
			String table = matchTable(uri);
			queryBuilder.setTables(table);

			SQLiteDatabase db = getDBHelper().getReadableDatabase();
			Cursor cursor = queryBuilder.query(db, // The database to
					// queryFromDB
					projection, // The columns to return from the queryFromDB
					selection, // The columns for the where clause
					selectionArgs, // The values for the where clause
					null, // don't group the rows
					null, // don't filter by row groups
					sortOrder // The sort order
					);

			cursor.setNotificationUri(getContext().getContentResolver(), uri);
			return cursor;
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		synchronized (DBLock) {
			SQLiteDatabase db = getDBHelper().getWritableDatabase();
			int count;
			String table = matchTable(uri);
			db.beginTransaction();
			try {
				count = db.update(table, values, selection, selectionArgs);
				db.setTransactionSuccessful();
			} finally {
				db.endTransaction();
			}
			getContext().getContentResolver().notifyChange(uri, null);

			return count;
		}
	}

	private String matchTable(Uri uri) {
		String table = null;
		switch (sUriMatcher.match(uri)) {
		case COMPANIES:
			table = CompanyDataHelper.CompanyDBInfo.TABLE_NAME;
			break;
		default:
			DebugLog.e("IllegalArgumentException:Unknown URI" + uri);
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		return table;
	}

}
