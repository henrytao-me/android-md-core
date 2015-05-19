/*
 * Copyright 2015 "Henry Tao <hi@henrytao.me>"
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.henrytao.mdwidget.activity;

import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;

import com.github.ksoichiro.android.observablescrollview.CacheFragmentStatePagerAdapter;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.github.ksoichiro.android.observablescrollview.Scrollable;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by henrytao on 5/17/15.
 */
public abstract class MdPagerTabActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

  public abstract int getPagerTabObservableScrollViewResource();

  protected abstract int getNumberOfTabs();

  protected abstract int getPagerContainerResource();

  protected abstract int getPagerHeaderResource();

  protected abstract int getPagerSlidingTabResource();

  protected abstract int getPagerStickyHeaderResource();

  protected abstract Fragment getPagerTabItemFragment(int position, int scrollY);

  protected abstract int getPagerTabItemSelectedIndicatorColors(int... colors);

  protected abstract View getPagerTabItemView(int position, ViewGroup parent);

  protected abstract int getPagerViewResource();

  protected abstract boolean isDistributeEvenly();

  public static final String ARG_SCROLL_Y = "arg_scroll_y";

  protected static final int SCROLL_ADJUSTMENT_IN_DP = 48;

  private int mBaseTranslationY;

  private NavigationAdapter mPagerAdapter;

  private int mScrollStartY;

  private int mScrollYAdjustmentInPixel = -1;

  private View vPagerContainer;

  private View vPagerHeader;

  private SlidingTabLayout vPagerSlidingTab;

  private View vPagerStickyHeader;

  private ViewPager vViewPager;

  @Override
  public void onDownMotionEvent() {

  }

  @Override
  public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
    if (dragging) {
      int toolbarHeight = getToolbarHeight();
      float currentHeaderTranslationY = ViewHelper.getTranslationY(getPagerHeader());
      if (firstScroll) {
        mScrollStartY = scrollY;
        if (-toolbarHeight < currentHeaderTranslationY) {
          mBaseTranslationY = scrollY;
        }
      }
      float headerTranslationY = ScrollUtils.getFloat(-(scrollY - mBaseTranslationY), -toolbarHeight, 0);
      ViewPropertyAnimator.animate(getPagerHeader()).cancel();
      ViewHelper.setTranslationY(getPagerHeader(), headerTranslationY);
    } else if (scrollY < mScrollStartY && toolbarIsHidden() && scrollY < getToolbarHeight()) {
      // scrolldown
      showToolbar();
    }
  }

  @Override
  public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    mBaseTranslationY = 0;
    Fragment fragment = getCurrentFragment();
    if (fragment == null) {
      return;
    }
    View view = fragment.getView();
    if (view == null) {
      return;
    }
    // ObservableXxxViews have same API but currently they don't have any common interfaces.
    adjustToolbar(scrollState, view);
  }

  @Override
  public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);
    initPagerTab();
  }

  @Override
  public void setContentView(View view) {
    super.setContentView(view);
    initPagerTab();
  }

  @Override
  public void setContentView(View view, ViewGroup.LayoutParams params) {
    super.setContentView(view, params);
    initPagerTab();
  }

  public View getPagerContainer() {
    if (vPagerContainer == null) {
      vPagerContainer = findViewById(getPagerContainerResource());
    }
    return vPagerContainer;
  }

  public View getPagerHeader() {
    if (vPagerHeader == null) {
      vPagerHeader = findViewById(getPagerHeaderResource());
    }
    return vPagerHeader;
  }

  public SlidingTabLayout getPagerSlidingTab() {
    if (vPagerSlidingTab == null) {
      vPagerSlidingTab = (SlidingTabLayout) findViewById(getPagerSlidingTabResource());
    }
    return vPagerSlidingTab;
  }

  public View getPagerStickyHeader() {
    if (vPagerStickyHeader == null) {
      vPagerStickyHeader = findViewById(getPagerStickyHeaderResource());
    }
    return vPagerStickyHeader;
  }

  public ViewPager getViewPager() {
    if (vViewPager == null) {
      vViewPager = (ViewPager) findViewById(getPagerViewResource());
    }
    return vViewPager;
  }

  public boolean keepPagerTabItemScrollPosition() {
    return true;
  }

  public boolean shouldScrollPagerTabItem(View view) {
    Scrollable scrollView = (Scrollable) view.findViewById(getPagerTabObservableScrollViewResource());
    if (scrollView == null) {
      return true;
    }
    int scrollY = scrollView.getCurrentScrollY();
    if (toolbarIsHidden() && scrollY >= 0 && scrollY <= getToolbarHeight()) {
      return true;
    }
    return false;
  }

  protected void adjustToolbar(ScrollState scrollState, View view) {
    final Scrollable scrollView = (Scrollable) view.findViewById(getPagerTabObservableScrollViewResource());
    if (scrollView == null) {
      return;
    }
    int scrollY = scrollView.getCurrentScrollY();
    if (scrollState == ScrollState.DOWN) {
      if (shouldShowToolbarIfScrollDown(
          view.findViewById(getPagerTabObservableScrollViewResource()),
          scrollY,
          mScrollStartY,
          getScrollYAdjustmentInPixel(),
          getPagerHeader().getHeight(),
          getToolbarHeight())) {
        showToolbar();
      }
    } else if (scrollState == ScrollState.UP) {
      if (shouldHideToolbarIfScrollUp(
          view.findViewById(getPagerTabObservableScrollViewResource()),
          scrollY,
          mScrollStartY,
          getScrollYAdjustmentInPixel(),
          getPagerHeader().getHeight(),
          getToolbarHeight())) {
        hideToolbar();
      } else {
        showToolbar();
      }
    } else {
      // Even if onScrollChanged occurs without scrollY changing, toolbar should be adjusted
      if (toolbarIsShown() || toolbarIsHidden()) {
        // Toolbar is completely moved, so just keep its state
        // and propagate it to other pages
        propagateToolbarState(toolbarIsShown());
      } else {
        // Toolbar is moving but doesn't know which to move:
        // you can change this to hideToolbar()
        showToolbar();
      }
    }
  }

  protected int getScrollYAdjustmentInDP() {
    return SCROLL_ADJUSTMENT_IN_DP;
  }

  protected boolean shouldHideToolbarIfScrollUp(View scroller, int currentScrollY, int startScrollY, int scrollYAdjustment,
      int headerHeight, int toolbarHeight) {
    if (currentScrollY >= toolbarHeight) {
      return true;
    }
    return false;
  }

  protected boolean shouldShowToolbarIfScrollDown(View scroller, int currentScrollY, int startScrollY, int scrollYAdjustment,
      int headerHeight, int toolbarHeight) {
    if (scrollYAdjustment == 0 ||
        startScrollY - currentScrollY > scrollYAdjustment ||
        currentScrollY < toolbarHeight) {
      return true;
    }
    return false;
  }

  private Fragment getCurrentFragment() {
    return mPagerAdapter.getItemAt(getViewPager().getCurrentItem());
  }

  private int getScrollYAdjustmentInPixel() {
    if (mScrollYAdjustmentInPixel < 0) {
      DisplayMetrics metrics = getResources().getDisplayMetrics();
      mScrollYAdjustmentInPixel = Math.round(getScrollYAdjustmentInDP() * (metrics.densityDpi / 160f));
      mScrollYAdjustmentInPixel = mScrollYAdjustmentInPixel < 0 ? 0 : mScrollYAdjustmentInPixel;
    }
    return mScrollYAdjustmentInPixel;
  }

  private int getToolbarHeight() {
    return getPagerHeader().getHeight() - getPagerStickyHeader().getHeight();
  }

  private void hideToolbar() {
    float headerTranslationY = ViewHelper.getTranslationY(getPagerHeader());
    int toolbarHeight = getToolbarHeight();
    if (headerTranslationY != -toolbarHeight) {
      ViewPropertyAnimator.animate(getPagerHeader()).cancel();
      ViewPropertyAnimator.animate(getPagerHeader()).translationY(-toolbarHeight).setDuration(200).start();
    }
    propagateToolbarState(false);
  }

  private void initPagerTab() {
    mPagerAdapter = new NavigationAdapter(this, getSupportFragmentManager());
    getViewPager().setAdapter(mPagerAdapter);

    SlidingTabLayout slidingTabLayout = getPagerSlidingTab();
    slidingTabLayout.setOnPopulateTabStripListener(new SlidingTabLayout.OnPopulateTabStripListener() {
      @Override
      public View onPopulateTabStrip(int position, ViewGroup parent) {
        return getPagerTabItemView(position, parent);
      }
    });
    slidingTabLayout.setSelectedIndicatorColors(getPagerTabItemSelectedIndicatorColors());
    slidingTabLayout.setDistributeEvenly(isDistributeEvenly());
    slidingTabLayout.setViewPager(getViewPager());

    // When the page is selected, other fragments' scrollY should be adjusted
    // according to the toolbar status(shown/hidden)
    slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrollStateChanged(int i) {
      }

      @Override
      public void onPageScrolled(int i, float v, int i2) {
      }

      @Override
      public void onPageSelected(int i) {
        propagateToolbarState(toolbarIsShown());
      }
    });
    propagateToolbarState(toolbarIsShown());
  }

  private void propagateToolbarState(boolean isShown) {
    int toolbarHeight = getToolbarHeight();
    // Set scrollY for the fragments that are not created yet
    mPagerAdapter.setScrollY(isShown ? 0 : toolbarHeight);
    // Set scrollY for the active fragments
    for (int i = 0; i < mPagerAdapter.getCount(); i++) {
      // Skip current item
      if (i == getViewPager().getCurrentItem()) {
        continue;
      }
      // Skip destroyed or not created item
      Fragment f = mPagerAdapter.getItemAt(i);
      if (f == null) {
        continue;
      }
      View view = f.getView();
      if (view == null) {
        continue;
      }
      propagateToolbarState(isShown, view, toolbarHeight);
    }
  }

  private void propagateToolbarState(boolean isShown, View view, int toolbarHeight) {
    Scrollable scrollView = (Scrollable) view.findViewById(getPagerTabObservableScrollViewResource());
    if (scrollView == null) {
      return;
    }
    if (isShown) {
      // Scroll up
      if (0 < scrollView.getCurrentScrollY()) {
        scrollView.scrollVerticallyTo(0);
      }
    } else {
      // Scroll down (to hide padding)
      if (scrollView.getCurrentScrollY() < toolbarHeight) {
        scrollView.scrollVerticallyTo(toolbarHeight);
      }
    }
  }

  private void showToolbar() {
    float headerTranslationY = ViewHelper.getTranslationY(getPagerHeader());
    if (headerTranslationY != 0) {
      ViewPropertyAnimator.animate(getPagerHeader()).cancel();
      ViewPropertyAnimator.animate(getPagerHeader()).translationY(0).setDuration(200).start();
    }
    propagateToolbarState(true);
  }

  private boolean toolbarIsHidden() {
    return ViewHelper.getTranslationY(getPagerHeader()) == -getToolbarHeight();
  }

  private boolean toolbarIsShown() {
    return ViewHelper.getTranslationY(getPagerHeader()) == 0;
  }

  /**
   * This adapter provides two types of fragments as an example.
   * {@linkplain #createItem(int)} should be modified if you use this example for your app.
   */
  private static class NavigationAdapter extends CacheFragmentStatePagerAdapter {

    private MdPagerTabActivity mContext;

    private int mScrollY;

    public NavigationAdapter(Context context, FragmentManager fm) {
      this(fm);
      mContext = context instanceof MdPagerTabActivity ? (MdPagerTabActivity) context : null;
    }

    public NavigationAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public int getCount() {
      return mContext == null ? 0 : mContext.getNumberOfTabs();
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return "";
    }

    @Override
    protected Fragment createItem(int position) {
      // Initialize fragments.
      // Please be sure to pass scroll position to each fragments using setArguments.
      Fragment fragment = null;
      if (mContext != null) {
        fragment = mContext.getPagerTabItemFragment(position, mScrollY);
        Bundle args = fragment.getArguments();
        args = args == null ? new Bundle() : args;
        if (mScrollY >= 0 && fragment instanceof ObservableScrollViewFragment) {
          args.putInt(ARG_SCROLL_Y, mScrollY);
        } else if (mScrollY > 0 && fragment instanceof ObservableListViewFragment) {
          args.putInt(ARG_SCROLL_Y, 1);
        } else if (mScrollY > 0 && fragment instanceof ObservableRecyclerViewFragment) {
          args.putInt(ARG_SCROLL_Y, 1);
        }
        fragment.setArguments(args);
      }
      return fragment;
    }

    public void setScrollY(int scrollY) {
      mScrollY = scrollY;
    }
  }

  public interface ObservableGridViewFragment {

  }

  public interface ObservableListViewFragment {

  }

  public interface ObservableRecyclerViewFragment {

  }

  public interface ObservableScrollViewFragment {

  }

  public interface ObservableWebViewFragment {

  }

}
