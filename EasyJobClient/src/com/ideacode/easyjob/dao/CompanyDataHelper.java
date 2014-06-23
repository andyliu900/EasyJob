package com.ideacode.easyjob.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v4.content.CursorLoader;

import com.ideacode.easyjob.model.Company;
import com.ideacode.easyjob.util.database.Column;
import com.ideacode.easyjob.util.database.Column.DataType;
import com.ideacode.easyjob.util.database.SQLiteTable;

/**
 * 企业搜索SQLite操作类
 * @author ly-suhai
 *
 */
public class CompanyDataHelper extends BaseDataHelper {

	public CompanyDataHelper(Context context) {
		super(context);
	}

	@Override
	protected Uri getContentUri() {
		return DataProvider.COMPANIES_CONTENT_URI;
	}

	private ContentValues getContentValues(Company company) {
		ContentValues values = new ContentValues();

		values.put(CompanyDBInfo.ID, company.id);
		values.put(CompanyDBInfo.COMPANY_NAME, company.company_name);
		values.put(CompanyDBInfo.COMPANY_ENNAME, company.company_enname);
		values.put(CompanyDBInfo.COMPANY_TYPE_ID, company.company_type_id);
		values.put(CompanyDBInfo.COMPANY_TYPE_NAME, company.company_type_name);
		values.put(CompanyDBInfo.COMPANY_LAT, company.company_lat);
		values.put(CompanyDBInfo.COMPANY_LON, company.company_lon);
		values.put(CompanyDBInfo.COMPANY_CITY, company.company_city);
		values.put(CompanyDBInfo.COMPANY_ADDRESS, company.company_address);
		values.put(CompanyDBInfo.COMPANY_APPRECIATE_COUNT, company.company_appreciate_count);
		values.put(CompanyDBInfo.COMPANY_COMMENT_COUNT, company.company_comment_count);
		values.put(CompanyDBInfo.COMPANY_CREATOR, company.company_creator);
		values.put(CompanyDBInfo.COMPANY_CREATE_DATE, company.company_create_date);
		values.put(CompanyDBInfo.COMPANY_CREATE_COMMENT, company.company_create_comment);
		values.put(CompanyDBInfo.JSON_IMAGES, company.imagesToJson(company.images));

		return values;
	}

	public Company query(long id) {
		Company company = null;
		Cursor cursor = query(null, CompanyDBInfo.ID + "= ?",
				new String[] {String.valueOf(id)}, null);
		if (cursor.moveToFirst()) {
			company = Company.fromCursor(cursor);
		}
		cursor.close();
		return company;
	}

	public void bulkInsert(List<Company> companies) {
		ArrayList<ContentValues> contentValues = new ArrayList<ContentValues>();
		for (Company company : companies) {
			ContentValues values = getContentValues(company);
			contentValues.add(values);
		}
		ContentValues[] valueArray = new ContentValues[contentValues.size()];
		bulkInsert(contentValues.toArray(valueArray));
	}

	public int deleteAll() {
		synchronized (DataProvider.DBLock) {
			DBHelper mDBHelper = DataProvider.getDBHelper();
			SQLiteDatabase db = mDBHelper.getWritableDatabase();
			int row = db.delete(CompanyDBInfo.TABLE_NAME, null, null);
			return row;
		}
	}

	public CursorLoader getCursorLoader() {
		return new CursorLoader(getContext(), getContentUri(), null, null,
				null, CompanyDBInfo._ID + " DESC");//倒序显示企业搜索结果
	}

	public static final class CompanyDBInfo implements BaseColumns {
		private CompanyDBInfo() {

		}

		public static final String TABLE_NAME = "companies";

		public static final String ID = "id";
		public static final String COMPANY_NAME = "company_name";
		public static final String COMPANY_ENNAME = "company_enname";
		public static final String COMPANY_TYPE_ID = "company_type_id";
		public static final String COMPANY_TYPE_NAME = "company_type_name";
		public static final String COMPANY_LAT = "company_lat";
		public static final String COMPANY_LON = "company_lon";
		public static final String COMPANY_CITY = "company_city";
		public static final String COMPANY_ADDRESS = "company_address";
		public static final String COMPANY_APPRECIATE_COUNT = "company_appreciate_count";
		public static final String COMPANY_COMMENT_COUNT = "company_comment_count";
		public static final String COMPANY_CREATOR = "company_creator";
		public static final String COMPANY_CREATE_DATE = "company_create_date";
		public static final String COMPANY_CREATE_COMMENT = "company_create_comment";
		public static final String JSON_IMAGES = "json_images";

		public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
		.addColumn(ID, Column.DataType.INTEGER)
		.addColumn(COMPANY_NAME, DataType.TEXT)
		.addColumn(COMPANY_ENNAME, Column.DataType.TEXT)
		.addColumn(COMPANY_TYPE_ID, Column.DataType.INTEGER)
		.addColumn(COMPANY_TYPE_NAME, Column.DataType.TEXT)
		.addColumn(COMPANY_LAT, Column.DataType.REAL)
		.addColumn(COMPANY_LON, Column.DataType.REAL)
		.addColumn(COMPANY_CITY, Column.DataType.TEXT)
		.addColumn(COMPANY_ADDRESS, Column.DataType.TEXT)
		.addColumn(COMPANY_APPRECIATE_COUNT, Column.DataType.INTEGER)
		.addColumn(COMPANY_COMMENT_COUNT, Column.DataType.INTEGER)
		.addColumn(COMPANY_CREATOR, Column.DataType.TEXT)
		.addColumn(COMPANY_CREATE_DATE, Column.DataType.TEXT)
		.addColumn(COMPANY_CREATE_COMMENT, Column.DataType.TEXT)
		.addColumn(JSON_IMAGES, Column.DataType.TEXT);
	}

}
