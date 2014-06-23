package com.ideacode.easyjob.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ideacode.easyjob.EasyJobApplication;
import com.ideacode.easyjob.R;
import com.ideacode.easyjob.api.ListSearchApi;
import com.ideacode.easyjob.data.GsonRequest;
import com.ideacode.easyjob.model.Company;
import com.ideacode.easyjob.ui.adapter.CompanyAdapter;
import com.ideacode.easyjob.util.ActionBarUtils;
import com.ideacode.easyjob.util.ListViewUtils;
import com.ideacode.easyjob.util.UITools;
import com.ideacode.easyjob.view.LoadingFooter;
import com.ideacode.easyjob.view.PageListView;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;


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
	private CompanyAdapter mAdapter;
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
		mAdapter = new CompanyAdapter(getActivity(), mListView);
		View header = new View(getActivity());
		mListView.addHeaderView(header);
		AnimationAdapter animationAdapter = new CardsAnimationAdapter(mAdapter);
		animationAdapter.setAbsListView(mListView);
		mListView.setAdapter(animationAdapter);
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

	private void loadData(int current_page) {
		if (!mSwipeLayout.isRefreshing() && (0 == current_page)) {
			mSwipeLayout.setRefreshing(true);
		}
		executeRequest(new GsonRequest(String.format(ListSearchApi.LIST, current_page), Company.CompanyRequestData.class, responseListener(), errorListener()));
	}

	private Response.Listener<Company.CompanyRequestData> responseListener() {
		final boolean isRefreshFromTop = ("0".equals(mPage));
		return new Response.Listener<Company.CompanyRequestData>() {
			@Override
			public void onResponse(final Company.CompanyRequestData response) {
				//                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
				//                    @Override
				//                    protected Object doInBackground(Object... params) {
				//                        if (isRefreshFromTop) {
				//                            mDataHelper.deleteAll();
				//                        }
				//                        mPage = response.getPage();
				//                        ArrayList<Feed> feeds = response.data;
				//                        mDataHelper.bulkInsert(feeds);
				//                        return null;
				//                    }
				//
				//                    @Override
				//                    protected void onPostExecute(Object o) {
				//                        super.onPostExecute(o);
				//                        if (isRefreshFromTop) {
				//                            mSwipeLayout.setRefreshing(false);
				//                        } else {
				//                            mListView.setState(LoadingFooter.State.Idle, 3000);
				//                        }
				//                    }
				//                });
			}
		};
	}

	@Override
	protected Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				UITools.ToastMessage(EasyJobApplication.getContext(), R.string.loading_failed);
				mSwipeLayout.setRefreshing(false);
				mListView.setState(LoadingFooter.State.Idle, 3000);
			}
		};
	}
}
