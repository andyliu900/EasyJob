package com.ideacode.easyjob.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import antonioleiva.navigationdrawercompat.ActionBarDrawerToggleCompat;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.actionbarsherlock.view.MenuItem;
import com.ideacode.easyjob.R;
import com.ideacode.easyjob.ui.fragment.BaseFragment;
import com.ideacode.easyjob.ui.fragment.CompanySearchFragment;
import com.ideacode.easyjob.util.UITools;

/**
 * Ö÷½çÃæ
 * @author ly-suhai
 *
 */
public class MainActivity extends BaseFragmentActivity implements AdapterView.OnItemClickListener{

	@InjectView(R.id.drawer_layout)DrawerLayout mDrawerLayout;
	@InjectView(R.id.left_drawer)ListView mDrawerList;
	private ActionBarDrawerToggleCompat mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mCategoryNames;

	private View view;
	private int keyBackClickCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = View.inflate(this, R.layout.activity_main, null);
		setContentView(view);
		ButterKnife.inject(this);

		initListeners();
		initDatas();

		if (savedInstanceState == null) {
			selectItem(0);
		}
	}

	@Override
	protected void initListeners() {
		mDrawerList.setOnItemClickListener(this);
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	@Override
	protected void initDatas() {
		mTitle = mDrawerTitle = getTitle();
		mCategoryNames = getResources().getStringArray(R.array.drawer_items);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mCategoryNames));

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggleCompat(
				this,                  /* host Activity */
				mDrawerLayout,         /* DrawerLayout object */
				R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open,  /* "open drawer" description for accessibility */
				R.string.drawer_close  /* "close drawer" description for accessibility */
				) {
			@Override
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		selectItem(position);
	}

	private void selectItem(int position) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		BaseFragment fragment = null;
		switch (position) {
		case 0:
			fragment = new CompanySearchFragment();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
			break;

		default:
			break;
		}

		mDrawerList.setItemChecked(position, true);
		setTitle(mCategoryNames[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
				switch (keyBackClickCount++) {
				case 0:
					UITools.ToastMessage(this, R.string.press_again_exit, 3);
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						@Override
						public void run() {
							keyBackClickCount = 0;
						}
					}, 2500);
					break;
				case 1:
					Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_out);
					anim.setAnimationListener(new AnimationListener() {
						@Override
						public void onAnimationEnd(Animation arg0) {
							application.exit(this);
						}

						@Override
						public void onAnimationRepeat(Animation animation) {
						}

						@Override
						public void onAnimationStart(Animation animation) {
						}

					});
					view.startAnimation(anim);
					break;
				default:
					break;
				}
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
