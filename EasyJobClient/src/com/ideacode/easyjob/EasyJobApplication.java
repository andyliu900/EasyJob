package com.ideacode.easyjob;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import android.app.Application;
import android.content.Context;

import com.ideacode.easyjob.ui.BaseFragmentActivity;
import com.ideacode.easyjob.util.DebugLog;

/**
 * ȫ��Ӧ����
 * @author ly-suhai
 *
 */
public class EasyJobApplication extends Application {

	private static final String TAG = EasyJobApplication.class.getSimpleName();

	private static Context sContext;
	/** activity���� */
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
	 * ��ָ���� {@link BaseFragmentActivity} ������ӵ� Activity �����С�
	 * 
	 * @param activity
	 *            ��ָ���� {@link BaseFragmentActivity} ����
	 */
	public void addActivity(final BaseFragmentActivity activity) {
		if (activity == null) {
			return;
		}

		mActivitySet.add(activity);
	}

	/**
	 * �Ƴ������� BaseFragmentActivity �����е� {@link BaseFragmentActivity} ����
	 * 
	 * @return {@code true} ��ʾ�Ƴ��ɹ�������Ϊ {@code false}��
	 */
	public boolean removeActivity(final BaseFragmentActivity activity) {
		if (activity == null) {
			return false;
		}

		return mActivitySet.remove(activity);
	}

	/**
	 * ��ȡָ�����ֵ� BaseFragmentActivity �����е� {@link BaseFragmentActivity} ����
	 * 
	 * @return activity��
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
	 * �ر����е�BaseFragmentActivity �����е� {@link BaseFragmentActivity} ����
	 */
	public final void finishAllActivities() {
		List<BaseFragmentActivity> activityList = new ArrayList<BaseFragmentActivity>(mActivitySet);
		for (BaseFragmentActivity activity : activityList) {
			removeActivity(activity);
			activity.finish();
		}
	}

	/**
	 * �˳� {@link BaseApplication}��ִ�к��ر����н��桢������ǰ���̡�
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
