package com.ideacode.easyjob.model;

import com.google.gson.Gson;

/**
 * ��toJson������ʵ�������
 * 
 * @author ��
 * 
 */
public class BaseModel {

	public String toJson() {
		return new Gson().toJson(this);
	}

}
