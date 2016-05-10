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

package me.henrytao.mdcore.core;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import me.henrytao.mdcore.R;
import me.henrytao.mdcore.utils.Ln;
import me.henrytao.mdcore.widgets.internal.MdCheckBox;

/**
 * Created by henrytao on 4/27/16.
 */
public class MdLayoutInflaterFactory implements LayoutInflaterFactory {

  private static final int FONT_WEIGHT_BLACK = 60;

  private static final int FONT_WEIGHT_BOLD = 50;

  private static final int FONT_WEIGHT_LIGHT = 20;

  private static final int FONT_WEIGHT_MEDIUM = 40;

  private static final int FONT_WEIGHT_REGULAR = 30;

  private static final int FONT_WEIGHT_THIN = 10;

  private static final Map<String, Typeface> sTypefaceCaches = new HashMap<>();

  private final AppCompatDelegate mDelegate;

  public MdLayoutInflaterFactory(@NonNull AppCompatDelegate delegate) {
    mDelegate = delegate;
  }

  @Override
  public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
    Ln.d("custom | %s | %s", name, attrs.getClass().toString());

    View view;
    if (TextUtils.equals(name, "checkbox")) {
      view = new MdCheckBox(context, attrs);
    } else {
      view = mDelegate.createView(parent, name, context, attrs);
    }

    supportImageView(context, view instanceof ImageView ? (ImageView) view : null, attrs);
    supportCheckBox(context, view instanceof CheckBox ? (CheckBox) view : null, attrs);
    supportButton(context, view instanceof Button ? (Button) view : null, attrs);
    supportTextView(context, view instanceof TextView ? (TextView) view : null, attrs);

    return view;
  }

  protected void supportButton(Context context, Button button, AttributeSet attrs) {
    if (button == null) {
      return;
    }
    ColorStateList textColor;
    TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextAppearance, 0, 0);
    try {
      textColor = MdCompat.getColorStateList(context, a.getResourceId(R.styleable.TextAppearance_android_textColor, 0));
    } catch (Exception ignore) {
      textColor = a.getColorStateList(R.styleable.TextAppearance_android_textColor);
    }
    a.recycle();
    if (textColor != null) {
      button.setTextColor(textColor);
    }
  }

  protected void supportCheckBox(Context context, CheckBox checkBox, AttributeSet attrs) {
    if (checkBox == null) {
      return;
    }
    Drawable drawable = null;
    TypedArray a = context.getTheme().obtainStyledAttributes(attrs, new int[]{
        R.attr.srcCompat
    }, 0, 0);
    try {
      int resId = a.getResourceId(0, 0);
      if (resId > 0) {
        drawable = MdVectorDrawableCompat.create(context, resId);
      }
    } catch (Exception ignore) {
    }
    a.recycle();
    if (drawable != null) {
      checkBox.setButtonDrawable(drawable);
    }
  }

  protected void supportImageView(Context context, ImageView imageView, AttributeSet attrs) {
    if (imageView == null) {
      return;
    }
    boolean isEnabled = true;
    TypedArray a = context.getTheme().obtainStyledAttributes(attrs, new int[]{
        R.attr.enabled
    }, 0, 0);
    try {
      isEnabled = a.getBoolean(0, true);
    } catch (Exception ignore) {
    }
    a.recycle();
    imageView.setEnabled(isEnabled);
  }

  protected void supportTextView(Context context, TextView textView, AttributeSet attrs) {
    if (textView == null) {
      return;
    }
    String typography = null;
    int fontWeight = 0;

    TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MdTypography, 0, 0);
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
    if (typeface != null) {
      textView.setTypeface(typeface);
    }
  }

  private Typeface getTypeface(Context context, String typography) {
    if (!sTypefaceCaches.containsKey(typography)) {
      sTypefaceCaches.put(typography, Typeface.createFromAsset(context.getAssets(), typography));
    }
    return sTypefaceCaches.get(typography);
  }
}
