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

import com.github.ksoichiro.android.observablescrollview.ObservableGridView;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ObservableWebView;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import me.henrytao.mdwidget.activity.MdPagerTabActivity;


/**
 * Created by henrytao on 5/17/15.
 */
public class MdPagerTabFragment extends Fragment {

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Activity parentActivity = getActivity();
    if (parentActivity instanceof MdPagerTabActivity) {
      MdPagerTabActivity pagerTabActivity = (MdPagerTabActivity) parentActivity;
      Bundle args = getArguments();
      if (args != null && args.containsKey(MdPagerTabActivity.ARG_SCROLL_Y)) {
        final int scrollY = args.getInt(MdPagerTabActivity.ARG_SCROLL_Y, 0);
        boolean keepPagerTabItemScrollPosition = pagerTabActivity.keepPagerTabItemScrollPosition();

        if (this instanceof MdPagerTabActivity.ObservableScrollViewFragment) {
          final ObservableScrollView scrollView = (ObservableScrollView) view
              .findViewById(pagerTabActivity.getPagerTabObservableScrollViewResource());
          if (!keepPagerTabItemScrollPosition) {
            ScrollUtils.addOnGlobalLayoutListener(scrollView, new Runnable() {
              @Override
              public void run() {
                scrollView.scrollTo(0, scrollY);
              }
            });
          }
          scrollView.setTouchInterceptionViewGroup((ViewGroup) pagerTabActivity.getPagerContainer());
          scrollView.setScrollViewCallbacks((ObservableScrollViewCallbacks) parentActivity);

        } else if (this instanceof MdPagerTabActivity.ObservableListViewFragment) {
          final ObservableListView listView = (ObservableListView) view
              .findViewById(pagerTabActivity.getPagerTabObservableScrollViewResource());
          if (!keepPagerTabItemScrollPosition) {
            ScrollUtils.addOnGlobalLayoutListener(listView, new Runnable() {
              @Override
              public void run() {
                listView.setSelection(scrollY);
              }
            });
          }
          listView.setTouchInterceptionViewGroup((ViewGroup) pagerTabActivity.getPagerContainer());
          listView.setScrollViewCallbacks((ObservableScrollViewCallbacks) parentActivity);

        } else if (this instanceof MdPagerTabActivity.ObservableRecyclerViewFragment) {
          final ObservableRecyclerView recyclerView = (ObservableRecyclerView) view
              .findViewById(pagerTabActivity.getPagerTabObservableScrollViewResource());
          if (!keepPagerTabItemScrollPosition) {
            ScrollUtils.addOnGlobalLayoutListener(recyclerView, new Runnable() {
              @Override
              public void run() {
                recyclerView.scrollVerticallyToPosition(scrollY);
              }
            });
          }
          recyclerView.setScrollViewCallbacks((ObservableScrollViewCallbacks) parentActivity);

        } else if (this instanceof MdPagerTabActivity.ObservableWebViewFragment) {
          final ObservableWebView webView = (ObservableWebView) view
              .findViewById(pagerTabActivity.getPagerTabObservableScrollViewResource());
          webView.setTouchInterceptionViewGroup((ViewGroup) pagerTabActivity.getPagerContainer());
          webView.setScrollViewCallbacks((ObservableScrollViewCallbacks) parentActivity);

        } else if (this instanceof MdPagerTabActivity.ObservableGridViewFragment) {
          final ObservableGridView gridView = (ObservableGridView) view
              .findViewById(pagerTabActivity.getPagerTabObservableScrollViewResource());
          gridView.setTouchInterceptionViewGroup((ViewGroup) pagerTabActivity.getPagerContainer());
          gridView.setScrollViewCallbacks((ObservableScrollViewCallbacks) parentActivity);
        }
      }
      // TouchInterceptionViewGroup should be a parent view other than ViewPager.
      // This is a workaround for the issue #117:
      // https://github.com/ksoichiro/Android-ObservableScrollView/issues/117
    }
  }

}
