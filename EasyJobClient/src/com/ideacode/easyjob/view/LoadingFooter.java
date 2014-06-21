package com.ideacode.easyjob.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ideacode.easyjob.R;

public class LoadingFooter {
	protected View mLoadingFooter;

	TextView mLoadingText;

	ProgressBar mProgress;

	protected State mState = State.Idle;

	private long mAnimationDuration;

	public static enum State {
		Idle, TheEnd, Loading
	}

	public LoadingFooter(Context context) {
		mLoadingFooter = LayoutInflater.from(context).inflate(R.layout.loading_footer, null);
		mLoadingFooter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// ÆÁ±Îµã»÷
			}
		});
		mLoadingText = (TextView) mLoadingFooter.findViewById(R.id.textView);
		mProgress = (ProgressBar) mLoadingFooter.findViewById(R.id.progressBar);
		mAnimationDuration = context.getResources().getInteger(
				android.R.integer.config_shortAnimTime);
		setState(State.Idle);
	}

	public View getView() {
		return mLoadingFooter;
	}

	public State getState() {
		return mState;
	}

	public void setState(final State state, long delay) {
		mLoadingFooter.postDelayed(new Runnable() {
			@Override
			public void run() {
				setState(state);
			}
		}, delay);
	}

	public void setState(State status) {
		if (mState == status) {
			return;
		}
		mState = status;

		mLoadingFooter.setVisibility(View.VISIBLE);

		switch (status) {
		case Loading:
			mLoadingText.setText(R.string.loading);
			mLoadingText.setVisibility(View.VISIBLE);
			mProgress.setVisibility(View.VISIBLE);
			break;
		case TheEnd:
			mLoadingText.setText(R.string.loading_end);
			mLoadingText.setVisibility(View.VISIBLE);
			mLoadingText.animate().withLayer().alpha(1).setDuration(mAnimationDuration);
			mProgress.setVisibility(View.GONE);
			break;
		default:
			mLoadingFooter.setVisibility(View.GONE);
			break;
		}
	}
}
