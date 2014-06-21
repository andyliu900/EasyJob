package com.ideacode.easyjob.util;

import android.app.Activity;
import android.view.View;

/**
 * actionbar������
 * @author ly-suhai
 *
 */
public class ActionBarUtils {
	public static View findActionBarContainer(Activity activity) {
		int id = activity.getResources().getIdentifier("action_bar_container", "id", "android");
		return activity.findViewById(id);
	}

	public static View findSplitActionBar(Activity activity) {
		int id = activity.getResources().getIdentifier("split_action_bar", "id", "android");
		return activity.findViewById(id);
	}
}
