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

package me.henrytao.mdcore.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.AttrRes;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.util.AttributeSet;

import java.util.HashMap;
import java.util.Map;

import me.henrytao.mdcore.R;
import me.henrytao.mdcore.core.MdCompat;

/**
 * Created by henrytao on 5/13/16.
 */
public class Typography {

  private static final int FONT_WEIGHT_BLACK = 60;

  private static final int FONT_WEIGHT_BOLD = 50;

  private static final int FONT_WEIGHT_LIGHT = 20;

  private static final int FONT_WEIGHT_MEDIUM = 40;

  private static final int FONT_WEIGHT_REGULAR = 30;

  private static final int FONT_WEIGHT_THIN = 10;

  private static final Map<String, Typeface> sTypefaceCaches = new HashMap<>();

  public static Typeface getTypeface(Context context, AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
    String typography = null;
    int fontWeight = 0;

    TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MdTypography, defStyleAttr, defStyleRes);
    try {
      fontWeight = a.getInt(R.styleable.MdTypography_mdFontWeight, 0);
      typography = a.getString(R.styleable.MdTypography_mdTypography);
    } catch (Exception ignore) {
    }
    a.recycle();

    Typeface typeface = null;
    if (TextUtils.isEmpty(typography) && fontWeight > 0) {
      switch (fontWeight) {
        case FONT_WEIGHT_THIN:
          typography = MdCompat.getStringFromAttribute(context, R.attr.mdTypography_thin);
          break;
        case FONT_WEIGHT_LIGHT:
          typography = MdCompat.getStringFromAttribute(context, R.attr.mdTypography_light);
          break;
        case FONT_WEIGHT_REGULAR:
          typography = MdCompat.getStringFromAttribute(context, R.attr.mdTypography_regular);
          break;
        case FONT_WEIGHT_MEDIUM:
          typography = MdCompat.getStringFromAttribute(context, R.attr.mdTypography_medium);
          break;
        case FONT_WEIGHT_BOLD:
          typography = MdCompat.getStringFromAttribute(context, R.attr.mdTypography_bold);
          break;
        case FONT_WEIGHT_BLACK:
          typography = MdCompat.getStringFromAttribute(context, R.attr.mdTypography_black);
          break;
      }
    }
    if (!TextUtils.isEmpty(typography)) {
      typeface = getTypeface(context, typography);
    }
    return typeface;
  }

  public static Typeface getTypeface(Context context, String typography) {
    if (!sTypefaceCaches.containsKey(typography)) {
      sTypefaceCaches.put(typography, Typeface.createFromAsset(context.getAssets(), typography));
    }
    return sTypefaceCaches.get(typography);
  }
}
