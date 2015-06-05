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
import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
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
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by henrytao on 5/17/15.
 */
public abstract class MdPagerTabActivity extends AppCompatActivity implements ObservableScrollViewCallbacks,
    ViewPager.OnPageChangeListener {

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

  private int mAnimationDelay = 100;

  private boolean mIsShowingToolbarWhenScrolling;

  private ScrollState mLastScrollState;

  private NavigationAdapter mPagerAdapter;

  private View vPagerContainer;

  private View vPagerHeader;

  private SlidingTabLayout vPagerSlidingTab;

  private View vPagerStickyHeader;

  private ViewPager vViewPager;

  @Override
  public void onDownMotionEvent() {

  }

  @Override
  public void onPageScrollStateChanged(int state) {

  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override
  public void onPageSelected(int position) {
    propagateToolbarState(toolbarIsShown());
    // check if current doesn't has height to scroll, then showToolbar
    Fragment f = mPagerAdapter.getItemAt(getViewPager().getCurrentItem());
    if (f == null) {
      return;
    }
    View view = f.getView();
    if (view == null) {
      return;
    }
    view = view.findViewById(getPagerTabObservableScrollViewResource());
    if (!(view instanceof Scrollable)) {
      return;
    }
    Scrollable scrollView = (Scrollable) view;
    if (toolbarIsShown()) {
      scrollView.scrollVerticallyTo(0);
    } else if (toolbarIsHidden() && shouldShowToolbarIfPageSelected(view, position)) {
      showToolbar();
    } else if (!toolbarIsHidden() && !toolbarIsShown()) {
      showToolbar();
    }
  }

  private boolean mIsHidingToolbarWhenScrolling;

  @Override
  public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
    int toolbarHeight = getToolbarHeight();
    float headerTranslationY = ScrollUtils.getFloat(-scrollY, -toolbarHeight, 0);
    if (dragging) {
      mLastScrollState = ScrollState.STOP;
      mIsShowingToolbarWhenScrolling = false;
    } else if (!mIsShowingToolbarWhenScrolling) {
      if (mLastScrollState == ScrollState.UP && !toolbarIsHidden()) {
        showToolbar();
      } else if (mLastScrollState == ScrollState.DOWN && !toolbarIsShown() && !toolbarIsHidden()) {
        showToolbar();
      } else if (mLastScrollState == ScrollState.DOWN && scrollY <= toolbarHeight) {
        showToolbar(false);
        mIsShowingToolbarWhenScrolling = true;
      }
    }
    if (!mIsShowingToolbarWhenScrolling) {
      ViewPropertyAnimator.animate(getPagerHeader()).cancel();
      ViewHelper.setTranslationY(getPagerHeader(), headerTranslationY);
    }
    if (mLastScrollState == ScrollState.UP && scrollY >= toolbarHeight) {
      propagateToolbarState(false);
    }
  }

  @Override
  public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    Fragment fragment = getCurrentFragment();
    if (fragment == null) {
      return;
    }
    View view = fragment.getView();
    if (view == null) {
      return;
    }
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

  public Fragment getCurrentFragment() {
    return mPagerAdapter.getItemAt(getViewPager().getCurrentItem());
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

  public int getToolbarHeight() {
    return getPagerHeader().getHeight() - getPagerStickyHeader().getHeight();
  }

  public ViewPager getViewPager() {
    if (vViewPager == null) {
      vViewPager = (ViewPager) findViewById(getPagerViewResource());
    }
    return vViewPager;
  }

  public void hideToolbar() {
    hideToolbar(true);
  }

  public void hideToolbar(boolean isDelay) {
    float headerTranslationY = ViewHelper.getTranslationY(getPagerHeader());
    int toolbarHeight = getToolbarHeight();
    if (headerTranslationY != -toolbarHeight) {
      ViewPropertyAnimator.animate(getPagerHeader()).cancel();
      if (isDelay) {
        ViewPropertyAnimator.animate(getPagerHeader()).translationY(-toolbarHeight).setStartDelay(mAnimationDelay).setDuration(200).start();
      } else {
        ViewPropertyAnimator.animate(getPagerHeader()).translationY(-toolbarHeight).setDuration(200).start();
      }
    }
    propagateToolbarState(false);
  }

  public boolean shouldScrollPagerTabItem(View view) {
    return false;
    //Scrollable scrollView = (Scrollable) view.findViewById(getPagerTabObservableScrollViewResource());
    //if (scrollView == null) {
    //  return true;
    //}
    //int scrollY = scrollView.getCurrentScrollY();
    //if (toolbarIsHidden() && scrollY >= 0 && scrollY <= getToolbarHeight()) {
    //  return true;
    //}
    //return false;
  }

  public void showToolbar() {
    showToolbar(true);
  }

  public void showToolbar(boolean isDelay) {
    float headerTranslationY = ViewHelper.getTranslationY(getPagerHeader());
    if (headerTranslationY != 0) {
      ViewPropertyAnimator.animate(getPagerHeader()).cancel();
      if (isDelay) {
        ViewPropertyAnimator.animate(getPagerHeader()).translationY(0).setStartDelay(mAnimationDelay).setDuration(200).start();
      } else {
        ViewPropertyAnimator.animate(getPagerHeader()).translationY(0).setDuration(200).start();
      }
    }
    propagateToolbarState(true);
  }

  public boolean toolbarIsHidden() {
    return ViewHelper.getTranslationY(getPagerHeader()) == -getToolbarHeight();
  }

  public boolean toolbarIsShown() {
    return ViewHelper.getTranslationY(getPagerHeader()) == 0;
  }

  protected void adjustToolbar(ScrollState scrollState, View view) {
    final Scrollable scrollView = (Scrollable) view.findViewById(getPagerTabObservableScrollViewResource());
    if (scrollView == null) {
      return;
    }
    //int scrollY = scrollView.getCurrentScrollY();
    if (scrollState == ScrollState.DOWN) {
      mLastScrollState = ScrollState.DOWN;
      if (!toolbarIsHidden()) {
        showToolbar();
      }
    } else if (scrollState == ScrollState.UP) {
      mLastScrollState = ScrollState.UP;
      if (!toolbarIsHidden() && !toolbarIsShown()) {
        showToolbar();
      }
    } else {
      mLastScrollState = ScrollState.STOP;
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

  protected boolean shouldShowToolbarIfPageSelected(View view, final int position) {
    if (view instanceof ObservableScrollView) {
      ObservableScrollView observableScrollView = (ObservableScrollView) view;
      if (observableScrollView.getCurrentScrollY() >= getToolbarHeight() ||
          observableScrollView.getChildAt(0).getHeight() >= view.getHeight() + getToolbarHeight()) {
        return false;
      }
    } else if (view instanceof ObservableRecyclerView) {
      ObservableRecyclerView observableRecyclerView = (ObservableRecyclerView) view;
      if (observableRecyclerView.getCurrentScrollY() >= getToolbarHeight() ||
          observableRecyclerView.computeVerticalScrollRange() >= view.getHeight() + getToolbarHeight()) {
        return false;
      }
    } else if (view instanceof ObservableListView) {
      ObservableListView observableListView = (ObservableListView) view;
      // Todo: need to test
      // return true;
    }
    return true;
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
    slidingTabLayout.setOnPageChangeListener(this);
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
