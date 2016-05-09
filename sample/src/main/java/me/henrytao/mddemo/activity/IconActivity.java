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
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.henrytao.mddemo.R;

/**
 * Created by henrytao on 5/2/16.
 */
public class IconActivity extends BaseActivity {

  @Bind(R.id.checkbox1)
  CheckBox vCheckBox1;

  @Bind(R.id.checkbox2)
  CheckBox vCheckBox2;

  @Bind(R.id.toolbar)
  Toolbar vToolbar;

  private int mCount;

  @Override
  protected int getDefaultLayout() {
    return R.layout.activity_icon;
  }

  @Override
  protected int getMdCoreLayout() {
    return R.layout.activity_icon;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);

    setSupportActionBar(vToolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    vToolbar.setNavigationOnClickListener(v -> onBackPressed());

    vCheckBox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
      mCount++;
      buttonView.setText(String.valueOf(mCount));
    });

    vCheckBox2.setOnCheckedChangeListener((buttonView, isChecked) -> {
      mCount++;
      buttonView.setText(String.valueOf(mCount));
    });
  }
}
