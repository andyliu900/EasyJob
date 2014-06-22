// Generated code from Butter Knife. Do not modify!
package com.ideacode.easyjob.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class CompanySearchFragment$$ViewInjector {
  public static void inject(Finder finder, final com.ideacode.easyjob.ui.fragment.CompanySearchFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131034165, "field 'mSwipeLayout'");
    target.mSwipeLayout = (android.support.v4.widget.SwipeRefreshLayout) view;
    view = finder.findRequiredView(source, 2131034166, "field 'mListView'");
    target.mListView = (com.ideacode.easyjob.view.PageListView) view;
  }

  public static void reset(com.ideacode.easyjob.ui.fragment.CompanySearchFragment target) {
    target.mSwipeLayout = null;
    target.mListView = null;
  }
}
