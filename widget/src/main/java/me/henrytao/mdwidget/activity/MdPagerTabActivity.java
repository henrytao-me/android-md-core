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

  protected abstract Fragment getPagerTabItemFragment(int position);

  protected abstract int getPagerTabItemSelectedIndicatorColors(int... colors);

  protected abstract View getPagerTabItemView(int position, ViewGroup parent);

  protected abstract int getPagerViewResource();

  protected abstract boolean isDistributeEvenly();

  private boolean mIsShowingToolbarWhenScrolling;

  private int mLastScrollY;

  private NavigationAdapter mPagerAdapter;

  private int mScrollIndex;

  private ScrollState mScrollState;

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
    View view = getScrollableView(getViewPager().getCurrentItem());
    if (view == null) {
      return;
    }
    Scrollable scrollView = (Scrollable) view;
    int toolbarHeight = getToolbarHeight();
    if (toolbarIsShown()) {
      scrollView.scrollVerticallyTo(0);
    } else if (toolbarIsHidden() && showToolbarIfPageSelected(view, position)) {
      showToolbar();
    } else if (!toolbarIsHidden() && !toolbarIsShown()) {
      showToolbar();
    } else if (scrollView.getCurrentScrollY() < toolbarHeight) {
      scrollView.scrollVerticallyTo(toolbarHeight);
    }
  }

  @Override
  public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
    if (firstScroll) {
      mScrollIndex = getViewPager().getCurrentItem();
      mLastScrollY = scrollY;
    }
    if (mScrollIndex == getViewPager().getCurrentItem()) {
      if (dragging) {
        onDragging(scrollY, firstScroll);
      } else {
        onScrolling(scrollY);
      }
    }
    mLastScrollY = scrollY;
  }

  @Override
  public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    Scrollable scrollView = getScrollable(getViewPager().getCurrentItem());
    if (scrollView == null) {
      return;
    }
    onHandUp(scrollView.getCurrentScrollY(), scrollState);
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

  public int getAnimationDuration() {
    return 200;
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

  public Scrollable getScrollable(int index) {
    View view = getScrollableView(index);
    if (view == null) {
      return null;
    }
    return (Scrollable) view;
  }

  public View getScrollableView(int index) {
    Fragment f = mPagerAdapter.getItemAt(index);
    if (f == null) {
      return null;
    }
    View view = f.getView();
    if (view == null) {
      return null;
    }
    view = view.findViewById(getPagerTabObservableScrollViewResource());
    if (!(view instanceof Scrollable)) {
      return null;
    }
    return view;
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
    float headerTranslationY = ViewHelper.getTranslationY(getPagerHeader());
    int toolbarHeight = getToolbarHeight();
    if (headerTranslationY != -toolbarHeight) {
      ViewPropertyAnimator.animate(getPagerHeader()).cancel();
      ViewPropertyAnimator.animate(getPagerHeader()).translationY(-toolbarHeight).setDuration(getAnimationDuration()).start();
    }
    propagateToolbarState(false);
  }

  public boolean keepStickyHeaderOnTop() {
    return false;
  }

  public void onDragging(int scrollY, boolean firstScroll) {
    if (firstScroll) {
      mScrollState = ScrollState.STOP;
      mIsShowingToolbarWhenScrolling = false;
    }
    int toolbarHeight = getToolbarHeight();
    float currentHeaderTranslationY = ViewHelper.getTranslationY(getPagerHeader());
    float headerTranslationY = ScrollUtils.getFloat(-(scrollY - mLastScrollY) + currentHeaderTranslationY, -toolbarHeight, 0);
    if (!(toolbarIsHidden() && scrollY < mLastScrollY && scrollY >= toolbarHeight)) {
      ViewPropertyAnimator.animate(getPagerHeader()).cancel();
      ViewHelper.setTranslationY(getPagerHeader(), headerTranslationY);
    }
  }

  public void onHandUp(int scrollY, ScrollState scrollState) {
    mScrollState = scrollState;
    int toolbarHeight = getToolbarHeight();
    if (scrollState == ScrollState.DOWN) {
      if (!toolbarIsHidden()) {
        showToolbar();
      }
    } else if (scrollState == ScrollState.UP) {
      if (scrollY < toolbarHeight) {
        showToolbar();
      } else {
        hideToolbar();
      }
    } else {
      // Even if onScrollChanged occurs without scrollY changing, toolbar should be adjusted
      if (toolbarIsShown() || toolbarIsHidden()) {
        propagateToolbarState(toolbarIsShown());
      } else {
        // Toolbar is moving but doesn't know which to move:
        // you can change this to hideToolbar()
        if (scrollY < toolbarHeight) {
          showToolbar();
        } else {
          hideToolbar();
        }
      }
    }
  }

  public void onScrolling(int scrollY) {
    int toolbarHeight = getToolbarHeight();
    float currentHeaderTranslationY = ViewHelper.getTranslationY(getPagerHeader());
    float headerTranslationY = ScrollUtils.getFloat(-(scrollY - mLastScrollY) + currentHeaderTranslationY, -toolbarHeight, 0);
    if ((mScrollState == ScrollState.DOWN || scrollY < mLastScrollY) && scrollY < toolbarHeight && !mIsShowingToolbarWhenScrolling) {
      mIsShowingToolbarWhenScrolling = true;
      showToolbar();
    } else if (mScrollState == ScrollState.UP || scrollY > mLastScrollY) {
      ViewPropertyAnimator.animate(getPagerHeader()).cancel();
      ViewHelper.setTranslationY(getPagerHeader(), headerTranslationY);
      if (scrollY < toolbarHeight) {
        showToolbar();
      } else {
        hideToolbar();
      }
    }
    //Log.i("onScrolling", String.format("%s | %d | %f | %f",
    //    mScrollState == ScrollState.DOWN ? "down" : (mScrollState == ScrollState.UP ? "up" : "unknow"),
    //    toolbarHeight, currentHeaderTranslationY, headerTranslationY));
  }

  public void showToolbar() {
    float headerTranslationY = ViewHelper.getTranslationY(getPagerHeader());
    if (headerTranslationY != 0) {
      ViewPropertyAnimator.animate(getPagerHeader()).cancel();
      ViewPropertyAnimator.animate(getPagerHeader()).translationY(0).setDuration(getAnimationDuration()).start();
    }
    propagateToolbarState(true);
  }

  public boolean toolbarIsHidden() {
    return ViewHelper.getTranslationY(getPagerHeader()) == -getToolbarHeight();
  }

  public boolean toolbarIsShown() {
    return ViewHelper.getTranslationY(getPagerHeader()) == 0;
  }

  protected boolean showToolbarIfPageSelected(View view, final int position) {
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
    for (int i = 0; i < mPagerAdapter.getCount(); i++) {
      // Skip current item
      if (i == getViewPager().getCurrentItem()) {
        continue;
      }
      Scrollable scrollView = getScrollable(i);
      if (scrollView == null) {
        return;
      }
      if (isShown) {
        // Scroll up
        if (0 < scrollView.getCurrentScrollY()) {
          scrollView.scrollVerticallyTo(0);
        }
      } else {
        // Scroll down (hide padding)
        if (scrollView.getCurrentScrollY() < toolbarHeight) {
          scrollView.scrollVerticallyTo(toolbarHeight);
        }
      }
    }
  }

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
      Fragment fragment = null;
      if (mContext != null) {
        fragment = mContext.getPagerTabItemFragment(position);
      }
      return fragment;
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
