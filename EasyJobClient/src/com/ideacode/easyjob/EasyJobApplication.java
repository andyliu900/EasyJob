package com.ideacode.easyjob;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import android.app.Application;
import android.content.Context;

import com.ideacode.easyjob.ui.BaseFragmentActivity;
import com.ideacode.easyjob.util.DebugLog;

/**
 * 全局应用类
 * @author ly-suhai
 *
 */
public class EasyJobApplication extends Application {

	private static final String TAG = EasyJobApplication.class.getSimpleName();

	private static Context sContext;
	/** activity集合 */
	private CopyOnWriteArraySet<BaseFragmentActivity> mActivitySet = new CopyOnWriteArraySet<BaseFragmentActivity>();

	@Override
	public void onCreate() {
		super.onCreate();
		sContext = getApplicationContext();
	}

	public static Context getContext() {
		return sContext;
	}

	/**
	 * 将指定的 {@link BaseFragmentActivity} 对象添加到 Activity 集合中。
	 * 
	 * @param activity
	 *            ：指定的 {@link BaseFragmentActivity} 对象。
	 */
	public void addActivity(final BaseFragmentActivity activity) {
		if (activity == null) {
			return;
		}

		mActivitySet.add(activity);
	}

	/**
	 * 移除并返回 BaseFragmentActivity 集合中的 {@link BaseFragmentActivity} 对象。
	 * 
	 * @return {@code true} 表示移除成功，否则为 {@code false}。
	 */
	public boolean removeActivity(final BaseFragmentActivity activity) {
		if (activity == null) {
			return false;
		}

		return mActivitySet.remove(activity);
	}

	/**
	 * 获取指定名字的 BaseFragmentActivity 集合中的 {@link BaseFragmentActivity} 对象。
	 * 
	 * @return activity。
	 */
	public BaseFragmentActivity getActivityByName(String aname) {
		for (BaseFragmentActivity ia : mActivitySet) {
			if (ia.getClass().getName().indexOf(aname) >= 0) {
				return ia;
			}

		}
		return null;
	}

	/**
	 * 关闭所有的BaseFragmentActivity 集合中的 {@link BaseFragmentActivity} 对象。
	 */
	public final void finishAllActivities() {
		List<BaseFragmentActivity> activityList = new ArrayList<BaseFragmentActivity>(mActivitySet);
		for (BaseFragmentActivity activity : activityList) {
			removeActivity(activity);
			activity.finish();
		}
	}

	/**
	 * 退出 {@link BaseApplication}，执行后会关闭所有界面、结束当前进程。
	 * 
	 * @param arg
	 */
	public final <T> void exit(T arg) {
		try {
			finishAllActivities();
		} catch (Exception e) {
			DebugLog.e("Failed to finish all activities." + e);
		}

		android.os.Process.killProcess(android.os.Process.myPid());

	}
}
