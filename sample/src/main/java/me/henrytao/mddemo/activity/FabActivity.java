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
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.henrytao.mdcore.widgets.FabSheetWindow;
import me.henrytao.mddemo.R;

/**
 * Created by henrytao on 5/5/16.
 */
public class FabActivity extends BaseActivity {

  @Bind(R.id.fab_popup_window) FloatingActionButton vFabPopupWindow;

  @Bind(R.id.toolbar) Toolbar vToolbar;

  @Override
  protected int getDefaultLayout() {
    return R.layout.activity_fab;
  }

  @Override
  protected int getMdCoreLayout() {
    return R.layout.activity_fab;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);

    setSupportActionBar(vToolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    vToolbar.setNavigationOnClickListener(v -> onBackPressed());
  }

  @OnClick(R.id.fab_popup_window)
  protected void onFabPopupWindowClick() {

    //FrameLayout frameLayout = new FrameLayout(this);
    //FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
    //    ViewGroup.LayoutParams.MATCH_PARENT);
    //frameLayout.setLayoutParams(layoutParams);
    //frameLayout.setBackgroundColor(Color.parseColor("#4C000000"));

    //PopupWindow popupWindow = new PopupWindow(frameLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    //popupWindow.setOutsideTouchable(true);
    //popupWindow.setFocusable(true);
    //popupWindow.setAnimationStyle(0);
    //popupWindow.getContentView().setFocusableInTouchMode(true);
    //popupWindow.showAsDropDown(getWindow().getDecorView());

    View sheet = getLayoutInflater().inflate(R.layout.custom_fab_sheet_window, null, false);
    new FabSheetWindow(vFabPopupWindow, sheet).show();
  }
}
