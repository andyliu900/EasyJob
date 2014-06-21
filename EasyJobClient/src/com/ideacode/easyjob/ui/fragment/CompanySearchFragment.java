package com.ideacode.easyjob.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.ideacode.easyjob.R;
import com.ideacode.easyjob.util.ActionBarUtils;
import com.ideacode.easyjob.util.ListViewUtils;
import com.ideacode.easyjob.view.PageListView;


/**
 * ÆóÒµËÑË÷fragment
 * @author ly-suhai
 *
 */
public class CompanySearchFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

	public static final String EXTRA_CATEGORY = "extra_category";

	@InjectView(R.id.swipe_container)
	SwipeRefreshLayout mSwipeLayout;

	@InjectView(R.id.listView)
	PageListView mListView;

	private View actionBarContainer;
	private int mPage = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_companysearch, container,
				false);
		ButterKnife.inject(this, mView);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	protected void initListeners() {
		actionBarContainer = ActionBarUtils.findActionBarContainer(getActivity());
		
		mListView.setLoadNextListener(new PageListView.OnLoadNextListener() {
			@Override
			public void onLoadNext() {
				loadNext();
			}
		});

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//                String imageUrl = mAdapter.getItem(position - mListView.getHeaderViewsCount()).images.large;
				//                Intent intent = new Intent(getActivity(), ImageViewActivity.class);
				//                intent.putExtra(ImageViewActivity.IMAGE_URL, imageUrl);
				//                startActivity(intent);
			}
		});

		actionBarContainer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ListViewUtils.smoothScrollListViewToTop(mListView);
			}
		});

		mSwipeLayout.setOnRefreshListener(this);
	}

	@Override
	protected void initDatas() {
		
	}

	@Override
	public void onRefresh() {

	}

	private void loadFirst() {
		mPage = 0;
		loadData(mPage);
	}

	private void loadNext() {
		loadData(mPage);
	}

	private void loadData(int next) {
		if (!mSwipeLayout.isRefreshing() && (0 == next)) {
			mSwipeLayout.setRefreshing(true);
		}
		//executeRequest(new GsonRequest(String.format(GagApi.LIST, mCategory.name(), next), Feed.FeedRequestData.class, responseListener(), errorListener()));
	}
}
