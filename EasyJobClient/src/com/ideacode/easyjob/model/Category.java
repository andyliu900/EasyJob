package com.ideacode.easyjob.model;

import android.test.suitebuilder.annotation.Suppress;

/**
 * 功能类型enum
 * @author ly-suhai
 *
 * 目前暂时没有必要使用
 */
@Deprecated
public enum Category {

	companysearch("Companysearch"),
	myquestions("MyQuestions"),
	mytrack("MyTrack"),
	workemotion("WorkEmotion");

	private String mDisplayName;

	Category(String displayName) {
		mDisplayName = displayName;
	}

	public String getDisplayName() {
		return mDisplayName;
	}

}
