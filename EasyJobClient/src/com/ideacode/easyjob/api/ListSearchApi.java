package com.ideacode.easyjob.api;

/**
 * 列表查询结果接口
 * @author 海
 *
 */
public class ListSearchApi {

	public static final String PAGE_SIZE = "20";
	
	private static final String HOST = "";
	
	/**
	 * 参数：current_page 当前页码
	 */
	public static final String LIST = HOST + "/%1$s/" + PAGE_SIZE; 
}
