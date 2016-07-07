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

package me.henrytao.mdcore.widgets.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

import me.henrytao.mdcore.R;
import me.henrytao.mdcore.utils.Typography;

/**
 * Created by henrytao on 5/13/16.
 */
public class MdToolbar extends Toolbar {

  private boolean mInit;

  private Typeface mSubtitleTypeface;

  private Typeface mTitleTypeface;

  public MdToolbar(Context context) {
    this(context, null);
  }

  public MdToolbar(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, R.attr.toolbarStyle);
  }

  public MdToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr > 0 ? defStyleAttr : R.attr.toolbarStyle);
    initFromAttributes(attrs, defStyleAttr > 0 ? defStyleAttr : R.attr.toolbarStyle);
  }

  @Override
  public void setSubtitle(CharSequence subtitle) {
    super.setSubtitle(Typography.applyTypeface(subtitle, mSubtitleTypeface));
  }

  @Override
  public void setTitle(CharSequence title) {
    super.setTitle(Typography.applyTypeface(title, mTitleTypeface));
  }

  private void initFromAttributes(AttributeSet attrs, int defStyleAttr) {
    TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, new int[]{
        R.attr.titleTextAppearance,
        R.attr.subtitleTextAppearance
    }, defStyleAttr, 0);
    int titleTextAppearanceResourceId = a.getResourceId(0, 0);
    int subtitleTextAppearanceResourceId = a.getResourceId(1, 0);
    a.recycle();
    if (titleTextAppearanceResourceId > 0) {
      mTitleTypeface = Typography.getTypeface(getContext(), titleTextAppearanceResourceId);
    }
    if (subtitleTextAppearanceResourceId > 0) {
      mSubtitleTypeface = Typography.getTypeface(getContext(), subtitleTextAppearanceResourceId);
    }
  }
}
