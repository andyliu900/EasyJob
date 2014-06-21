package com.ideacode.easyjob.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 界面工具类
 * @author ly-suhai
 *
 */
public class UITools {

	/**
	 * 弹出Toast消息
	 * 
	 * @param msg
	 */
	public static void ToastMessage(Context cont, String msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, int msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, String msg, int time) {
		Toast.makeText(cont, msg, time).show();
	}

	public static void ToastMessage(Context cont, int msg, int time) {
		Toast.makeText(cont, msg, time).show();
	}

}
