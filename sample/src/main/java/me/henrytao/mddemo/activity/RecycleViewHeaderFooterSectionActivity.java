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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.henrytao.mddemo.R;
import me.henrytao.mddemo.adapter.ChatItemAdapter;
import me.henrytao.mddemo.util.ResourceUtils;
import me.henrytao.mdwidget.activity.MdToolbarActivity;

public class RecycleViewHeaderFooterSectionActivity extends MdToolbarActivity {

  public static Intent getIntent(Context context) {
    Intent intent = new Intent(context, RecycleViewHeaderFooterSectionActivity.class);
    return intent;
  }

  @InjectView(R.id.scrollView)
  RecyclerView vRecyclerView;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_recycle_view_header_footer_section, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected int getToolbarResource() {
    return R.id.toolbar;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recycle_view_header_footer_section);
    ButterKnife.inject(this);

    ChatItemAdapter mChatItemAdapter = new ChatItemAdapter(
        this,
        ResourceUtils.inflate(this, R.layout.item_recycle_view_header),
        ResourceUtils.inflate(this, R.layout.item_recycle_view_footer),
        null);
    vRecyclerView.setHasFixedSize(false);
    vRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    vRecyclerView.setAdapter(mChatItemAdapter);
  }
}
