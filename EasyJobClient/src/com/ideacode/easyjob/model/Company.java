package com.ideacode.easyjob.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.database.Cursor;

import com.google.gson.Gson;
import com.ideacode.easyjob.dao.CompanyDataHelper;

/**
 * 企业实体类
 * @author 海
 *
 */
public class Company extends BaseModel {

	private static final HashMap<String,Company> CACHE = new HashMap<String,Company>();

	public int id;
	public String company_name;
	public String company_enname;
	public int company_type_id;
	public String company_type_name;
	public float company_lat;
	public float company_lon;
	public String company_city;
	public String company_address;
	public int company_appreciate_count;
	public int company_comment_count;
	public String company_creator;
	public String company_create_date;
	public String company_create_comment;
	public CompanyImage images;

	public class CompanyImage{
		public String small;
		public List<String> list;
	}

	public static String imagesToJson(CompanyImage images){
		return new Gson().toJson(images, CompanyImage.class);
	}

	private static void addToCache(Company company) {
		CACHE.put(String.valueOf(company.id), company);
	}

	private static Company getFromCache(String id) {
		return CACHE.get(id);
	}

	public static Company fromJson(String json) {
		return new Gson().fromJson(json, Company.class);
	}

	/**
	 * 从本地SQLite获取资料
	 * @param cursor
	 * @return
	 */
	public static Company fromCursor(Cursor cursor) {
		String id = cursor.getString(cursor.getColumnIndex(CompanyDataHelper.CompanyDBInfo.ID));
		Company company = getFromCache(id);
		if (company != null) {
			return company;
		}

		//		company = new Gson().fromJson(
		//				cursor.getString(cursor.getColumnIndex(CompanyDataHelper.CompanyDBInfo.JSON)),
		//				Company.class);
		addToCache(company);
		return company;
	}

	public static class CompanyRequestData {
		public ArrayList<Company> data;
		public Paging paging;

		public String getPage() {
			return paging.next_page;
		}
	}

	private class Paging {
		public String next_page;
	}
}
