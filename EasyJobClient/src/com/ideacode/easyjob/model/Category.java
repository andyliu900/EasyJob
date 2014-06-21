package com.ideacode.easyjob.model;

/**
 * ��������enum
 * @author ly-suhai
 *
 */
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
