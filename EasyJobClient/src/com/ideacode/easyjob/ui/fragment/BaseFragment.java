package com.ideacode.easyjob.ui.fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ideacode.easyjob.EasyJobApplication;
import com.ideacode.easyjob.api.RequestManager;
import com.ideacode.easyjob.util.UITools;

/**
 * Fragment»ùÀà
 * @author ly-suhai
 *
 */
public abstract class BaseFragment extends SherlockFragment implements Runnable {

	protected View mView;
	protected List<Handler> mHandlers = new ArrayList<Handler>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initListeners();
		initDatas();
		return mView;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		RequestManager.cancelAll(this);
	}

	protected void executeRequest(Request request) {
		RequestManager.addRequest(request, this);
	}

	protected Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				UITools.ToastMessage(EasyJobApplication.getContext(), error.getMessage());
			}
		};
	}

	protected void putInHandlerList(Handler handler){
		mHandlers.add(handler);
	}

	protected void clearHandlerList(){
		Iterator<Handler> iterator = mHandlers.iterator();
		while (iterator.hasNext()) {
			Handler handler = iterator.next();
			if (handler != null) {
				handler.removeCallbacks(this);
			}
		}
		mHandlers.clear();
	}

	@Override
	public void run() {

	}

	protected abstract void initListeners();

	protected abstract void initDatas();

}
