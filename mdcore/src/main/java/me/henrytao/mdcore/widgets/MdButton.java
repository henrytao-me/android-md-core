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

package me.henrytao.mdcore.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import me.henrytao.mdcore.R;
import me.henrytao.mdcore.core.MdCompat;

/**
 * Created by henrytao on 4/27/16.
 */
public class MdButton extends AppCompatButton {

  public MdButton(Context context) {
    super(context);
    initFromAttributes(null, 0);
  }

  public MdButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    initFromAttributes(attrs, 0);
  }

  public MdButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initFromAttributes(attrs, defStyleAttr);
  }

  @Override
  public void setTextColor(int color) {
    super.setTextColor(color);
  }

  protected void initFromAttributes(AttributeSet attrs, int defStyleAttr) {
    Context context = getContext();

    ColorStateList textColor;
    TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextAppearance, defStyleAttr, 0);
    try {
      textColor = MdCompat.getColorStateList(context, a.getResourceId(R.styleable.TextAppearance_android_textColor, 0));
    } catch (Exception ignore) {
      textColor = a.getColorStateList(R.styleable.TextAppearance_android_textColor);
    }
    a.recycle();
    if (textColor != null) {
      setTextColor(textColor);
    }
  }
}
