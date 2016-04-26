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

package me.henrytao.mdcore.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import java.util.HashMap;
import java.util.Map;

import me.henrytao.mdcore.R;
import me.henrytao.mdcore.utils.ResourceUtils;

/**
 * Created by henrytao on 10/24/15.
 */
public class MdButton extends AppCompatButton {

  protected static final int DEFAULT_TYPE = 11;

  protected static Map<Integer, Integer> sButtonInfos = new HashMap<>();

  static {
    sButtonInfos.put(11, R.attr.MdButtonStyle);
    sButtonInfos.put(12, R.attr.MdButtonColoredStyle);
    sButtonInfos.put(13, R.attr.MdButtonBorderlessStyle);
    sButtonInfos.put(14, R.attr.MdButtonBorderlessColoredStyle);
  }

  protected static int getDefaultStyleAttr(Context context, AttributeSet attrs, int styleAttr) {
    if (styleAttr > 0) {
      return styleAttr;
    }
    TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MdButton, 0, 0);
    int defStyleAttr = sButtonInfos.get(DEFAULT_TYPE);
    try {
      int type = a.getInteger(R.styleable.MdButton_mdb_type, DEFAULT_TYPE);
      if (sButtonInfos.containsKey(type)) {
        defStyleAttr = sButtonInfos.get(type);
      }
    } finally {
      a.recycle();
    }
    return defStyleAttr;
  }

  public MdButton(Context context) {
    this(context, null);
  }

  public MdButton(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MdButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, getDefaultStyleAttr(context, attrs, defStyleAttr));
    initFromAttributes(attrs, getDefaultStyleAttr(context, attrs, defStyleAttr));
  }

  protected void initFromAttributes(AttributeSet attrs, int defStyleAttr) {
    Context context = getContext();

    ColorStateList textColor;
    TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextAppearance, defStyleAttr, 0);
    try {
      textColor = ResourceUtils.createColorStateListFromResId(context, a.getResourceId(R.styleable.TextAppearance_android_textColor, 0));
    } catch (Exception ignore) {
      textColor = a.getColorStateList(R.styleable.TextAppearance_android_textColor);
    }
    a.recycle();
    if (textColor != null) {
      setTextColor(textColor);
    }
  }
}
