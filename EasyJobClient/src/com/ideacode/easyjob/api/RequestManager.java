package com.ideacode.easyjob.api;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.ideacode.easyjob.EasyJobApplication;

/**
 * VolleyÕ¯¬Á«Î«Ûπ‹¿Ì∆˜
 * @author ly-suhai
 *
 */
public class RequestManager {

	public static RequestQueue mRequestQueue = Volley.newRequestQueue(EasyJobApplication.getContext());

	private RequestManager() {
		// no instances
	}

	public static void addRequest(Request<?> request, Object tag) {
		if (tag != null) {
			request.setTag(tag);
		}
		mRequestQueue.add(request);
	}

	public static void cancelAll(Object tag) {
		mRequestQueue.cancelAll(tag);
	}
}
