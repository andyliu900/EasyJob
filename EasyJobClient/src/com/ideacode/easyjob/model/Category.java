package com.ideacode.easyjob.model;

import android.test.suitebuilder.annotation.Suppress;

/**
 * ��������enum
 * @author ly-suhai
 *
 * Ŀǰ��ʱû�б�Ҫʹ��
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
