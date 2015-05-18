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
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import me.henrytao.mdwidget.activity.MdPagerTabActivity;


/**
 * Created by henrytao on 5/17/15.
 */
public class MdPagerTabFragment extends Fragment {

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    observeListView(view);
    observeRecycleView(view);
    observeScrollView(view);
  }

  private void observeListView(View view) {
    Activity parentActivity = getActivity();
    if (parentActivity instanceof MdPagerTabActivity && this instanceof MdPagerTabActivity.ObservableListViewFragment) {
      MdPagerTabActivity pagerTabActivity = (MdPagerTabActivity) getActivity();
      final ObservableListView listView = (ObservableListView) view
          .findViewById(pagerTabActivity.getPagerTabObservableScrollViewResource());
      // Scroll to the specified position after layout
      Bundle args = getArguments();
      if (args != null && args.containsKey(MdPagerTabActivity.ARG_SCROLL_Y)) {
        final int initialPosition = args.getInt(MdPagerTabActivity.ARG_SCROLL_Y, 0);
        ScrollUtils.addOnGlobalLayoutListener(listView, new Runnable() {
          @Override
          public void run() {
            // scrollTo() doesn't work, should use setSelection()
            listView.setSelection(initialPosition);
          }
        });
      }
      // TouchInterceptionViewGroup should be a parent view other than ViewPager.
      // This is a workaround for the issue #117:
      // https://github.com/ksoichiro/Android-ObservableScrollView/issues/117
      listView.setTouchInterceptionViewGroup((ViewGroup) pagerTabActivity.getPagerContainer());
      listView.setScrollViewCallbacks((ObservableScrollViewCallbacks) parentActivity);
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
      final WeakReference<MdPagerTabActivity> pagerActivityWeakReference = new WeakReference<>(pagerActivity);
      final WeakReference<ObservableRecyclerView> recyclerViewWeakReference = new WeakReference<>(recyclerView);
      // Scroll to the specified offset after layout
      Bundle args = getArguments();
      if (args != null && args.containsKey(MdPagerTabActivity.ARG_SCROLL_Y)) {
        final int initialPosition = args.getInt(MdPagerTabActivity.ARG_SCROLL_Y, 0);
        ScrollUtils.addOnGlobalLayoutListener(recyclerView, new Runnable() {
          @Override
          public void run() {
            if (pagerActivityWeakReference.get() == null || recyclerViewWeakReference.get() == null) {
              return;
            }
            MdPagerTabActivity activity = pagerActivityWeakReference.get();
            ObservableRecyclerView view = recyclerViewWeakReference.get();
            if (!activity.keepPagerTabItemScrollPosition() || activity.shouldScrollPagerTabItem(view)) {
              view.scrollVerticallyToPosition(initialPosition);
            }
          }
        });
      }
      recyclerView.setScrollViewCallbacks(pagerActivity);
    }
  }

  private void observeScrollView(View view) {
    if (getActivity() instanceof MdPagerTabActivity && this instanceof MdPagerTabActivity.ObservableScrollViewFragment) {
      MdPagerTabActivity pagerActivity =
          (MdPagerTabActivity) getActivity();
      ObservableScrollView scrollView =
          (ObservableScrollView) view.findViewById(pagerActivity.getPagerTabObservableScrollViewResource());
      final WeakReference<MdPagerTabActivity> pagerActivityWeakReference = new WeakReference<>(pagerActivity);
      final WeakReference<ObservableScrollView> scrollViewWeakReference = new WeakReference<>(scrollView);
      // Scroll to the specified offset after layout
      Bundle args = getArguments();
      if (args != null && args.containsKey(MdPagerTabActivity.ARG_SCROLL_Y)) {
        final int scrollY = args.getInt(MdPagerTabActivity.ARG_SCROLL_Y, 0);
        ScrollUtils.addOnGlobalLayoutListener(scrollView, new Runnable() {
          @Override
          public void run() {
            if (pagerActivityWeakReference.get() == null || scrollViewWeakReference.get() == null) {
              return;
            }
            MdPagerTabActivity activity = pagerActivityWeakReference.get();
            ObservableScrollView view = scrollViewWeakReference.get();
            if (!activity.keepPagerTabItemScrollPosition() || activity.shouldScrollPagerTabItem(view)) {
              view.scrollTo(0, scrollY);
            }
          }
        });
      }
      // TouchInterceptionViewGroup should be a parent view other than ViewPager.
      // This is a workaround for the issue #117:
      // https://github.com/ksoichiro/Android-ObservableScrollView/issues/117
      scrollView.setTouchInterceptionViewGroup((ViewGroup) pagerActivity.getPagerContainer());
      scrollView.setScrollViewCallbacks(pagerActivity);
    }
  }


}
