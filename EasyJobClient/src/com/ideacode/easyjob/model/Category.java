package com.ideacode.easyjob.model;

/**
 * 功能类型enum
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
