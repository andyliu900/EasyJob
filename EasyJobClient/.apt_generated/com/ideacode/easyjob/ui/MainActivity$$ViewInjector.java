// Generated code from Butter Knife. Do not modify!
package com.ideacode.easyjob.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainActivity$$ViewInjector {
  public static void inject(Finder finder, final com.ideacode.easyjob.ui.MainActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131034164, "field 'mDrawerList'");
    target.mDrawerList = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131034162, "field 'mDrawerLayout'");
    target.mDrawerLayout = (android.support.v4.widget.DrawerLayout) view;
  }

  public static void reset(com.ideacode.easyjob.ui.MainActivity target) {
    target.mDrawerList = null;
    target.mDrawerLayout = null;
  }
}
