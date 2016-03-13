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

package me.henrytao.mddemo.adapter;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import me.henrytao.mddemo.R;
import me.henrytao.mddemo.holder.ThemeViewHolder;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeViewHolder> {

  private final ThemeViewHolder.OnItemClickListener mOnItemClickListener;

  private List<ThemeItem> mItems;

  public ThemeAdapter(ThemeViewHolder.OnItemClickListener onItemClickListener) {
    mOnItemClickListener = onItemClickListener;
    mItems = Arrays.asList(
        new ThemeItem(R.id.action_theme_red, R.drawable.ic_theme_red, "Red"),
        new ThemeItem(R.id.action_theme_pink, R.drawable.ic_theme_pink, "Pink"),
        new ThemeItem(R.id.action_theme_purple, R.drawable.ic_theme_purple, "Purple"),
        new ThemeItem(R.id.action_theme_deep_purple, R.drawable.ic_theme_deep_purple, "Deep Purple"),
        new ThemeItem(R.id.action_theme_indigo, R.drawable.ic_theme_indigo, "Indigo"),
        new ThemeItem(R.id.action_theme_blue, R.drawable.ic_theme_blue, "Blue"),
        new ThemeItem(R.id.action_theme_light_blue, R.drawable.ic_theme_light_blue, "Light Blue"),
        new ThemeItem(R.id.action_theme_cyan, R.drawable.ic_theme_cyan, "Cyan"),
        new ThemeItem(R.id.action_theme_teal, R.drawable.ic_theme_teal, "Teal"),
        new ThemeItem(R.id.action_theme_green, R.drawable.ic_theme_green, "Green"),
        new ThemeItem(R.id.action_theme_light_green, R.drawable.ic_theme_light_green, "Light Green"),
        new ThemeItem(R.id.action_theme_lime, R.drawable.ic_theme_lime, "Lime"),
        new ThemeItem(R.id.action_theme_yellow, R.drawable.ic_theme_yellow, "Yellow"),
        new ThemeItem(R.id.action_theme_amber, R.drawable.ic_theme_amber, "Amber"),
        new ThemeItem(R.id.action_theme_orange, R.drawable.ic_theme_orange, "Orange"),
        new ThemeItem(R.id.action_theme_deep_orange, R.drawable.ic_theme_deep_orange, "Deep Orange"),
        new ThemeItem(R.id.action_theme_brown, R.drawable.ic_theme_brown, "Brown"),
        new ThemeItem(R.id.action_theme_grey, R.drawable.ic_theme_grey, "Grey"),
        new ThemeItem(R.id.action_theme_blue_grey, R.drawable.ic_theme_blue_grey, "Blue Grey"),
        new ThemeItem(R.id.action_theme_white, R.drawable.ic_theme_white, "White"),
        new ThemeItem(R.id.action_theme_black, R.drawable.ic_theme_black, "Black")
    );
  }

  @Override
  public int getItemCount() {
    return mItems.size();
  }

  @Override
  public void onBindViewHolder(ThemeViewHolder holder, int position) {
    ThemeItem item = mItems.get(position);
    holder.bind(item.mId, item.mIcon, item.mTitle);
  }

  @Override
  public ThemeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ThemeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theme, null, false), mOnItemClickListener);
  }

  private static class ThemeItem {

    @DrawableRes
    private final int mIcon;

    private final int mId;

    private final String mTitle;

    public ThemeItem(int id, @DrawableRes int icon, String title) {
      mId = id;
      mIcon = icon;
      mTitle = title;
    }
  }
}