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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.henrytao.mddemo.R;
import me.henrytao.mdwidget.activity.MdDrawerLayoutActivity;

public class DrawerLayoutActivity extends MdDrawerLayoutActivity {

  public static Intent getIntent(Context context) {
    return new Intent(context, DrawerLayoutActivity.class);
  }

  @InjectView(R.id.toolbar)
  Toolbar vToolbar;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_drawer_layout, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_navigation_drawer_right:
        openDrawer(NAVIGATION_DRAWER_TYPE.RIGHT);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected int getDrawerContentResource() {
    return R.id.content;
  }

  @Override
  protected int getDrawerLayoutResource() {
    return R.id.container;
  }

  @Override
  protected int getDrawerNavigationResource() {
    return R.id.navigation;
  }

  @Override
  protected int getDrawerNavigationRightResource() {
    return R.id.navigation_right;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_drawer_layout);
    ButterKnife.inject(this);

    setSupportActionBar(vToolbar);
    vToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openDrawer();
      }
    });
  }
}
