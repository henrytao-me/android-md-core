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

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.henrytao.mdcore.utils.ResourceUtils;
import me.henrytao.mddemo.R;
import me.henrytao.smoothappbarlayout.SmoothCollapsingToolbarLayout;

/**
 * Created by henrytao on 10/15/15.
 */
public class BaseLayoutActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

  protected static final long DRAWER_CLOSE_DELAY = 200;

  private static final int BACK_PRESSED_DELAY = 2000;

  private static Handler sHandler = new Handler();

  private static boolean sIsBackPressed;

  @Bind(R.id.collapsing_toolbar_layout)
  CollapsingToolbarLayout vCollapsingToolbarLayout;

  @Bind(R.id.drawer_layout)
  DrawerLayout vDrawerLayout;

  @Bind(R.id.navigation_view)
  NavigationView vNavigationView;

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
  public void onBackPressed() {
    if (vDrawerLayout.isDrawerOpen(GravityCompat.START)) {
      vDrawerLayout.closeDrawer(GravityCompat.START);
    } else if (!sIsBackPressed && isTaskRoot()) {
      Toast.makeText(this, R.string.text_back_pressed_confirmation, Toast.LENGTH_SHORT).show();
      sIsBackPressed = true;
      sHandler.postDelayed(() -> sIsBackPressed = false, BACK_PRESSED_DELAY);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    mActionBarDrawerToggle.onConfigurationChanged(newConfig);
  }

  @Override
  public boolean onNavigationItemSelected(final MenuItem item) {
    vDrawerLayout.closeDrawer(GravityCompat.START);
    vDrawerLayout.postDelayed(() -> BaseLayoutActivity.this.onNavigationItemSelected(item.getItemId()), DRAWER_CLOSE_DELAY);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void setTitle(CharSequence title) {
    super.setTitle("");
    mTitle = title;
    if (vTitle != null) {
      vTitle.setText(mTitle);
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

    vNavigationView.setNavigationItemSelectedListener(this);
  }

  protected void onNavigationItemSelected(int id) {
    Intent intent = null;
    switch (id) {
      case R.id.menu_introduction:
        if (!(this instanceof IntroductionActivity)) {
          intent = IntroductionActivity.newIntent(this);
        }
        break;
      case R.id.menu_lists:
        if (!(this instanceof ListsActivity)) {
          intent = ListsActivity.newIntent(this);
        }
        break;
    }
    if (intent != null) {
      intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
    }
  }
}
