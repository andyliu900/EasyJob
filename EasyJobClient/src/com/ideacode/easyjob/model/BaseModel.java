package com.ideacode.easyjob.model;

import com.google.gson.Gson;

/**
 * 带toJson方法的实体类基类
 * 
 * @author 海
 * 
 */
public class BaseModel {

	public String toJson() {
		return new Gson().toJson(this);
	}

}
