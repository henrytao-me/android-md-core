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

package me.henrytao.mddemo.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.henrytao.mddemo.R;

/**
 * Created by henrytao on 5/17/15.
 */
public class ItemTabIndicator extends BaseHolder<ItemTabIndicator> {

  @InjectView(R.id.title)
  TextView vTitle;

  public ItemTabIndicator(Context context) {
    super(context);
  }

  public ItemTabIndicator(Context context, ViewGroup parent) {
    super(context, parent);
  }

  public ItemTabIndicator setTitle(CharSequence title) {
    vTitle.setText(title);
    return this;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.item_tab_indicator;
  }

  @Override
  protected void onCreate(Context context, View view) {
    ButterKnife.inject(this, view);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    ButterKnife.reset(this);
  }
}
