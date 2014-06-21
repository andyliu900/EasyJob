package com.ideacode.easyjob.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ideacode.easyjob.EasyJobApplication;
import com.ideacode.easyjob.R;
import com.ideacode.easyjob.api.RequestManager;
import com.ideacode.easyjob.util.UITools;

/**
 * FragmentActivity»ùÀà
 * @author ly-suhai
 *
 */
public abstract class BaseFragmentActivity extends SherlockFragmentActivity implements Runnable {

	protected EasyJobApplication application;
	protected ActionBar actionBar;
	protected List<Handler> mHandlers = new ArrayList<Handler>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		application = (EasyJobApplication)getApplication();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		clearHandlerList();
		RequestManager.cancelAll(this);
	}

	protected void executeRequest(Request<?> request) {
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			overridePendingTransition(R.anim.swip_stay, R.anim.swip_out);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.swip_stay, R.anim.swip_out);
	}

	@Override
	public void run() {

	}

	protected abstract void initListeners();

	protected abstract void initDatas();


}
