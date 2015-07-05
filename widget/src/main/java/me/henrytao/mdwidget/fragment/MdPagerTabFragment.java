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

package me.henrytao.mdwidget.fragment;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import me.henrytao.mdwidget.activity.MdPagerTabActivity;

/**
 * Created by henrytao on 5/17/15.
 */
public class MdPagerTabFragment extends Fragment implements MdPagerTabActivity.MdPagerTabListeners {

  @Override
  public void onPagerSelected(int scrollY, boolean toolbarIsShown) {

  }

  @Override
  public void onPagerTabDragging(int scrollY, boolean firstScroll) {

  }

  @Override
  public void onPagerTabHandUp(int scrollY, ScrollState scrollState) {

  }

  @Override
  public void onPagerTabScrolling(int scrollY) {

  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    observeListView(view);
    observeRecycleView(view);
    observeScrollView(view);
  }

  private void observeListView(View view) {
    if (getActivity() instanceof MdPagerTabActivity && this instanceof MdPagerTabActivity.ObservableListViewFragment) {
      MdPagerTabActivity pagerActivity =
          (MdPagerTabActivity) getActivity();
      ObservableListView listView =
          (ObservableListView) view.findViewById(pagerActivity.getPagerTabObservableScrollViewResource());
      // TouchInterceptionViewGroup should be a parent view other than ViewPager.
      // This is a workaround for the issue #117:
      // https://github.com/ksoichiro/Android-ObservableScrollView/issues/117
      listView.setTouchInterceptionViewGroup((ViewGroup) pagerActivity.getPagerContainer());
      listView.setScrollViewCallbacks(pagerActivity);
    }
  }

  private void observeRecycleView(View view) {
    if (getActivity() instanceof MdPagerTabActivity && this instanceof MdPagerTabActivity.ObservableRecyclerViewFragment) {
      MdPagerTabActivity pagerActivity =
          (MdPagerTabActivity) getActivity();
      ObservableRecyclerView recyclerView =
          (ObservableRecyclerView) view.findViewById(pagerActivity.getPagerTabObservableScrollViewResource());
      recyclerView.setLayoutManager(new LinearLayoutManager(pagerActivity));
      recyclerView.setHasFixedSize(false);
      // Scroll to the specified offset after layout
      recyclerView.setScrollViewCallbacks(pagerActivity);
    }
  }

  private void observeScrollView(View view) {
    if (getActivity() instanceof MdPagerTabActivity && this instanceof MdPagerTabActivity.ObservableScrollViewFragment) {
      MdPagerTabActivity pagerActivity =
          (MdPagerTabActivity) getActivity();
      ObservableScrollView scrollView =
          (ObservableScrollView) view.findViewById(pagerActivity.getPagerTabObservableScrollViewResource());
      // TouchInterceptionViewGroup should be a parent view other than ViewPager.
      // This is a workaround for the issue #117:
      // https://github.com/ksoichiro/Android-ObservableScrollView/issues/117
      scrollView.setTouchInterceptionViewGroup((ViewGroup) pagerActivity.getPagerContainer());
      scrollView.setScrollViewCallbacks(pagerActivity);
    }
  }
}
