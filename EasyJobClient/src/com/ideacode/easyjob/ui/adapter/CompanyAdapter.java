package com.ideacode.easyjob.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.android.volley.toolbox.ImageLoader;
import com.ideacode.easyjob.R;
import com.ideacode.easyjob.data.ImageCacheManager;
import com.ideacode.easyjob.model.Company;

/**
 * 企业搜索列表页适配器--用CursorAdapter进行适配
 * 
 * @author 海
 * 
 */
public class CompanyAdapter extends CursorAdapter {

	private Context mContext;

	private LayoutInflater mLayoutInflater;

	private ListView mListView;

	private Drawable mDefaultImageDrawable = new ColorDrawable(Color.argb(255, 201, 201, 201));

	public CompanyAdapter(Context context, ListView listView) {
		super(context, null, false);
		mContext = context;
		mLayoutInflater = ((Activity) context).getLayoutInflater();
		mListView = listView;
	}

	@Override
	public Company getItem(int position) {
		mCursor.moveToPosition(position);
		return Company.fromCursor(mCursor);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		return mLayoutInflater.inflate(R.layout.listitem_company, null);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		Holder holder = getHolder(view);
		if (holder.imageRequest != null) {
			holder.imageRequest.cancelRequest();
		}

		view.setEnabled(!mListView.isItemChecked(cursor.getPosition()
				+ mListView.getHeaderViewsCount()));

		Company company = Company.fromCursor(cursor);
		holder.companyname.setText(company.company_name);
		String listitem_secondtext = mContext.getResources()
				.getString(R.string.listitem_secondtext);
		holder.appreandcomment.setText(String.format(listitem_secondtext, company.company_appreciate_count,company.company_comment_count));
		holder.imageRequest = ImageCacheManager.loadImage(company.images.small,
				ImageCacheManager.getImageListener(holder.image, mDefaultImageDrawable, mDefaultImageDrawable));
		holder.creatorcomment.setText(company.company_create_comment);
	}

	private Holder getHolder(final View view) {
		Holder holder = (Holder) view.getTag();
		if (holder == null) {
			holder = new Holder(view);
			view.setTag(holder);
		}
		return holder;
	}

	static class Holder {
		@InjectView(R.id.tv_companyname)
		TextView companyname;

		@InjectView(R.id.tv_appreandcomment)
		TextView appreandcomment;

		@InjectView(R.id.iv_normal)
		ImageView image;

		@InjectView(R.id.tv_creatorcomment)
		TextView creatorcomment;

		public ImageLoader.ImageContainer imageRequest;

		public Holder(View view) {
			ButterKnife.inject(this, view);
		}
	}
}
