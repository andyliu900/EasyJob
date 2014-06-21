package com.ideacode.easyjob.util;

import android.os.Build;
import android.widget.ListView;

/**
 * 列表工具类  列表滚动至顶部动画效果
 * @author ly-suhai
 *
 */
public class ListViewUtils {
	private ListViewUtils() {

	}

	/**
	 * 滚动列表到顶端
	 *
	 * @param listView
	 */
	public static void smoothScrollListViewToTop(final ListView listView) {
		if (listView == null) {
			return;
		}
		smoothScrollListView(listView, 0);
		listView.postDelayed(new Runnable() {
			@Override
			public void run() {
				listView.setSelection(0);
			}
		}, 200);
	}

	/**
	 * 滚动列表到position
	 *
	 * @param listView
	 * @param position
	 */
	public static void smoothScrollListView(ListView listView, int position) {
		if (Build.VERSION.SDK_INT > 7) {
			listView.smoothScrollToPositionFromTop(0, 0);
		} else {
			listView.setSelection(position);
		}
	}
}
