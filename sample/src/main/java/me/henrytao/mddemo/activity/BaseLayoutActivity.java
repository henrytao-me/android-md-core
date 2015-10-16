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

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.henrytao.mdcore.utils.ResourceUtils;
import me.henrytao.mddemo.R;
import me.henrytao.smoothappbarlayout.SmoothCollapsingToolbarLayout;

/**
 * Created by henrytao on 10/15/15.
 */
public class BaseLayoutActivity extends BaseActivity {

  @Bind(R.id.collapsing_toolbar_layout)
  CollapsingToolbarLayout vCollapsingToolbarLayout;

  @Bind(R.id.drawer_layout)
  DrawerLayout vDrawerLayout;

  @Bind(R.id.smooth_collapsing_toolbar_layout)
  SmoothCollapsingToolbarLayout vSmoothCollapsingToolbarLayout;

  @Bind(R.id.title)
  TextView vTitle;

  @Bind(R.id.toolbar)
  Toolbar vToolbar;

  @Bind(R.id.translucent_status_holder)
  View vTranslucentStatusHolder;

  private ActionBarDrawerToggle mActionBarDrawerToggle;

  private CharSequence mTitle;

  @Override
  public void setTitle(CharSequence title) {
    super.setTitle("");
    mTitle = title;
    if (vTitle != null) {
      vTitle.setText(title);
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base_layout);
    ButterKnife.bind(this);
    setSupportActionBar(vToolbar);

    boolean isFitSystemWindows = ViewCompat.getFitsSystemWindows(vDrawerLayout);
    vTranslucentStatusHolder.setVisibility(isFitSystemWindows ? View.VISIBLE : View.GONE);
    if (isFitSystemWindows) {
      ResourceUtils.enableTranslucentStatus(this);
    }

    vCollapsingToolbarLayout.setTitleEnabled(false);
    setTitle(getTitle());

    mActionBarDrawerToggle = new ActionBarDrawerToggle(this, vDrawerLayout, vToolbar, R.string.text_open, R.string.text_close);
    vDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    mActionBarDrawerToggle.syncState();
  }
}
