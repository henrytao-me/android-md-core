/*
 * Copyright 2016 "Henry Tao <hi@henrytao.me>"
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
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.henrytao.mdcore.core.MdCompat;
import me.henrytao.mddemo.R;
import me.henrytao.mddemo.config.Constants;

/**
 * Created by henrytao on 4/26/16.
 */
public class MainActivity extends BaseActivity {

  @Bind(R.id.drawer_layout)
  DrawerLayout vDrawerLayout;

  @Bind(R.id.navigation_view_left)
  NavigationView vNavigationViewLeft;

  @Bind(R.id.navigation_view_right)
  NavigationView vNavigationViewRight;

  @Bind(R.id.toolbar)
  Toolbar vToolbar;

  private ActionBarDrawerToggle mActionBarDrawerToggle;

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    mActionBarDrawerToggle.onConfigurationChanged(newConfig);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_item_right_navigation:
        vDrawerLayout.openDrawer(GravityCompat.END);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected int getDefaultLayout() {
    return 0;
  }

  @Override
  protected int getMdCoreLayout() {
    return R.layout.activity_main;
  }

  @Override
  protected int getOptionsMenuId() {
    return R.menu.menu_main;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);

    boolean isFitSystemWindows = ViewCompat.getFitsSystemWindows(vDrawerLayout);
    if (isFitSystemWindows) {
      MdCompat.enableTranslucentStatus(this);
    }

    setSupportActionBar(vToolbar);

    mActionBarDrawerToggle = new ActionBarDrawerToggle(this, vDrawerLayout, vToolbar, R.string.text_open, R.string.text_close);
    vDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
    mActionBarDrawerToggle.syncState();

    vNavigationViewLeft.setNavigationItemSelectedListener(item -> onNavigationItemSelected(GravityCompat.START, item));
    vNavigationViewRight.setNavigationItemSelectedListener(item -> onNavigationItemSelected(GravityCompat.END, item));

    ImageView leftLogo = (ImageView) vNavigationViewLeft.getHeaderView(0).findViewById(R.id.google_logo);
    MdCompat.supportDrawableTint(this, leftLogo.getDrawable(), MdCompat.Palette.BACKGROUND);
    ImageView rightLogo = (ImageView) vNavigationViewRight.getHeaderView(0).findViewById(R.id.google_logo);
    MdCompat.supportDrawableTint(this, rightLogo.getDrawable(), MdCompat.Palette.BACKGROUND);
  }

  private boolean onNavigationItemSelected(@Gravity int type, MenuItem item) {
    vDrawerLayout.closeDrawers();
    vDrawerLayout.postDelayed(() -> onNavigationItemSelected(type, item.getItemId()), Constants.Timer.SHORT);
    return true;
  }

  private void onNavigationItemSelected(@Gravity int type, int menuItemId) {
    Intent intent = null;
    switch (menuItemId) {
      case R.id.menu_icon:
        intent = new Intent(this, IconActivity.class);
        break;
      case R.id.menu_button:
        intent = new Intent(this, ButtonActivity.class);
        break;
      case R.id.menu_fab:
        intent = new Intent(this, FabActivity.class);
        break;
      case R.id.menu_card:
        intent = new Intent(this, CardActivity.class);
        break;
      case R.id.menu_dialog:
        intent = new Intent(this, DialogActivity.class);
        break;
      case R.id.menu_divider:
        intent = new Intent(this, DividerActivity.class);
        break;
      case R.id.menu_subheader:
        intent = new Intent(this, SubheaderActivity.class);
        break;
      case R.id.menu_list:
        intent = new Intent(this, ListActivity.class);
        break;
      case R.id.menu_text_field:
        intent = new Intent(this, TextFieldActivity.class);
        break;
      case R.id.menu_help_and_feedback:
        intent = new Intent(this, InfoActivity.class);
        break;
    }
    if (intent != null) {
      intent.putExtra(Constants.Extra.IS_MD_CORE_ENABLED, type == GravityCompat.START);
      startActivity(intent);
    }
  }

  @IntDef({GravityCompat.START, GravityCompat.END})
  @Retention(RetentionPolicy.SOURCE)
  private @interface Gravity {

  }
}
