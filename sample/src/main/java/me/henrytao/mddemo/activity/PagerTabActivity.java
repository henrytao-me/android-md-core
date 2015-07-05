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

package me.henrytao.mddemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.henrytao.mddemo.R;
import me.henrytao.mddemo.fragment.PagerTabAFragment;
import me.henrytao.mddemo.fragment.PagerTabBFragment;
import me.henrytao.mddemo.fragment.PagerTabCFragment;
import me.henrytao.mddemo.holder.ItemTabIndicator;
import me.henrytao.mddemo.util.ResourceUtils;
import me.henrytao.mdwidget.activity.MdPagerTabActivity;
import me.henrytao.mdwidget.fragment.MdPagerTabFragment;

public class PagerTabActivity extends MdPagerTabActivity {

  public static Intent getIntent(Context context) {
    return new Intent(context, PagerTabActivity.class);
  }

  @InjectView(R.id.toolbar)
  Toolbar vToolbar;

  @Override
  public int getPagerTabObservableScrollViewResource() {
    return R.id.scrollView;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_pager_tab, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected Fragment createPagerTabItemFragment(int position) {
    MdPagerTabFragment fragment = null;
    switch (position) {
      case 0:
        fragment = new PagerTabAFragment();
        break;
      case 1:
        fragment = new PagerTabBFragment();
        break;
      case 2:
        fragment = new PagerTabCFragment();
        break;
    }
    return fragment;
  }

  @Override
  protected View createPagerTabItemView(int position, ViewGroup parent) {
    ItemTabIndicator tab = new ItemTabIndicator(this, parent);
    switch (position) {
      case 0:
        tab.setTitle("Tab A");
        break;
      case 1:
        tab.setTitle("Tab B");
        break;
      case 2:
        tab.setTitle("Tab C");
        break;
    }
    return tab.getView();
  }

  @Override
  protected int getNumberOfTabs() {
    return 3;
  }

  @Override
  protected int getPagerContainerResource() {
    return R.id.container;
  }

  @Override
  protected int getPagerHeaderResource() {
    return R.id.header;
  }

  @Override
  protected int getPagerSlidingTabResource() {
    return R.id.sliding_tab;
  }

  @Override
  protected int getPagerStickyHeaderResource() {
    return R.id.sliding_tab;
  }

  @Override
  protected int getPagerTabItemSelectedIndicatorColors(int... colors) {
    return ResourceUtils.getColorFromAttribute(this, R.attr.mdColor_accentPalette);
  }

  @Override
  protected int getPagerViewResource() {
    return R.id.view_pager;
  }

  @Override
  protected boolean isDistributeEvenly() {
    // Todo: change this to false if you have too many tabs
    return true;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pager_tab);
    ButterKnife.inject(this);

    setSupportActionBar(vToolbar);
    vToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });

    // workaround for fixing toolbar height issue in landscape mode
    ViewGroup.LayoutParams params = vToolbar.getLayoutParams();
    params.height = ResourceUtils.getActionBarSizeInPixel(this) * 2;
  }
}
