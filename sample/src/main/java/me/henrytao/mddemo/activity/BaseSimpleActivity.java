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
import android.support.annotation.LayoutRes;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import me.henrytao.mdcore.utils.ResourceUtils;
import me.henrytao.mddemo.R;
import me.henrytao.mddemo.adapter.ThemeAdapter;
import me.henrytao.mddemo.utils.ThemeUtils;

/**
 * Created by henrytao on 10/15/15.
 */
public abstract class BaseSimpleActivity extends BaseActivity {

  @LayoutRes
  public abstract int getLayoutId();

  protected ViewGroup vContainer;

  protected Toolbar vToolbar;

  @Override
  public void onBackPressed() {
    if (isTaskRoot()) {
      startActivity(IntroductionActivity.newIntent(this));
      finish();
    } else {
      super.onBackPressed();
    }
    overridePendingTransition(R.anim.enter_ltr, R.anim.exit_ltr);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    ResourceUtils.supportDrawableTint(this, menu, ResourceUtils.Palette.PRIMARY);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_theme:
        showThemePicker();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base_simple);

    vContainer = (ViewGroup) findViewById(R.id.container);
    vToolbar = (Toolbar) findViewById(R.id.toolbar);

    setSupportActionBar(vToolbar);
    vToolbar.setNavigationOnClickListener(v -> onBackPressed());
    ResourceUtils.supportDrawableTint(this, vToolbar, ResourceUtils.Palette.PRIMARY);

    if (getLayoutId() > 0) {
      LayoutInflater.from(this).inflate(getLayoutId(), vContainer, true);
    }
  }

  protected void showThemePicker() {
    BottomSheetDialog dialog = new BottomSheetDialog(this);
    View view = getLayoutInflater().inflate(R.layout.custom_color_dialog, null, false);
    RecyclerView recyclerView = (RecyclerView) view.findViewById(android.R.id.list);
    recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    recyclerView.setAdapter(new ThemeAdapter((v, id) -> ThemeUtils.changeToTheme(this, id)));
    dialog.setContentView(view);
    dialog.show();
  }
}
