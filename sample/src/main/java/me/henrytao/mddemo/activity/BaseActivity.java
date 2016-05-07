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

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import me.henrytao.mdcore.core.MdCore;
import me.henrytao.mddemo.R;
import me.henrytao.mddemo.adapter.ThemeAdapter;
import me.henrytao.mddemo.config.Constants;
import me.henrytao.mddemo.utils.ThemeUtils;

/**
 * Created by henrytao on 4/26/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

  @LayoutRes
  protected abstract int getDefaultLayout();

  @LayoutRes
  protected abstract int getMdCoreLayout();

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    if (getOptionsMenu() > 0) {
      getMenuInflater().inflate(getOptionsMenu(), menu);
    }
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_item_palette:
        showThemePicker();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    ThemeUtils.onCreate(this);
    boolean isMdCoreEnabled = isMdCoreEnabled();
    if (isMdCoreEnabled) {
      MdCore.init(this);
    }
    super.onCreate(savedInstanceState);
    setContentView(isMdCoreEnabled ? getMdCoreLayout() : getDefaultLayout());
  }

  public boolean isMdCoreEnabled() {
    return this instanceof MainActivity || getIntent().getBooleanExtra(Constants.Extra.IS_MD_CORE_ENABLED, false);
  }

  protected int getOptionsMenu() {
    //return R.menu.menu_default;
    return 0;
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
