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

package me.henrytao.mdcore.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by henrytao on 4/28/15.
 */
public abstract class MdToolbarActivity extends AppCompatActivity {

  protected abstract int getToolbarResource();

  protected Toolbar vToolbar;

  @Override
  public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);
    initToolbar();
  }

  @Override
  public void setContentView(View view) {
    super.setContentView(view);
    initToolbar();
  }

  @Override
  public void setContentView(View view, ViewGroup.LayoutParams params) {
    super.setContentView(view, params);
    initToolbar();
  }

  protected int getToolbarContentResource() {
    return 0;
  }

  protected void initToolbar() {
    if (getToolbarResource() > 0) {
      vToolbar = (Toolbar) findViewById(getToolbarResource());
      setSupportActionBar(vToolbar);
      vToolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          onNavigationClicked(view);
        }
      });
      if (getToolbarContentResource() > 0) {
        vToolbar.addView(getLayoutInflater().inflate(getToolbarContentResource(), vToolbar, false));
      }
    }
  }

  protected void onNavigationClicked(View view) {
    onBackPressed();
  }

}
