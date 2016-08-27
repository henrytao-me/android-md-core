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
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DialogTitle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import me.henrytao.mdcore.R;
import me.henrytao.mdcore.utils.Ln;
import me.henrytao.mdcore.utils.Typography;
import me.henrytao.mdcore.widgets.internal.MdCheckBox;
import me.henrytao.mdcore.widgets.internal.MdToolbar;

/**
 * Created by henrytao on 4/27/16.
 */
public class MdLayoutInflaterFactory implements LayoutInflaterFactory {

  private final AppCompatDelegate mDelegate;

  public MdLayoutInflaterFactory(@NonNull AppCompatDelegate delegate) {
    mDelegate = delegate;
  }

  @Override
  public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
    Ln.d("custom | %s | %s", name, attrs.getClass().toString());
    View view = onCreateCustomView(parent, name, context, attrs);
    onSupportView(view, parent, name, context, attrs);
    return view;
  }

  protected View onCreateCustomView(View parent, String name, Context context, AttributeSet attrs) {
    if (TextUtils.equals(name, "CheckBox")) {
      return new MdCheckBox(context, attrs);
    } else if (TextUtils.equals(name, "android.support.v7.widget.Toolbar")) {
      return new MdToolbar(context, attrs);
    } else if (TextUtils.equals(name, "android.support.v7.widget.DialogTitle")) {
      return new DialogTitle(context, attrs);
    }
    return mDelegate.createView(parent, name, context, attrs);
  }

  protected void onSupportView(View view, View parent, String name, Context context, AttributeSet attrs) {
    supportImageView(context, view instanceof ImageView ? (ImageView) view : null, attrs);
    supportCheckBox(context, view instanceof CheckBox ? (CheckBox) view : null, attrs);
    supportButton(context, view instanceof Button ? (Button) view : null, attrs);
    supportTextView(context, view instanceof TextView ? (TextView) view : null, attrs);
  }

  protected void supportButton(Context context, Button view, AttributeSet attrs) {
    if (view == null) {
      return;
    }
    ColorStateList textColor;
    TypedArray a = context.getTheme().obtainStyledAttributes(attrs, new int[]{
        android.R.attr.textColor
    }, R.attr.buttonStyle, 0);
    try {
      textColor = MdCompat.getColorStateList(context, a.getResourceId(0, 0));
    } catch (Exception ignore) {
      textColor = a.getColorStateList(0);
    }
    a.recycle();
    if (textColor != null) {
      view.setTextColor(textColor);
    }
  }

  protected void supportCheckBox(Context context, CheckBox view, AttributeSet attrs) {
    if (view == null) {
      return;
    }
    Drawable drawable = MdVectorDrawableCompat.getFromAttribute(context, attrs, R.attr.srcCompat);
    if (drawable != null) {
      view.setButtonDrawable(drawable);
    }
  }

  protected void supportImageView(Context context, ImageView view, AttributeSet attrs) {
    if (view == null) {
      return;
    }
    supportVectorDrawable(context, view, attrs);
    boolean isEnabled = true;
    TypedArray a = context.getTheme().obtainStyledAttributes(attrs, new int[]{
        R.attr.enabled
    }, 0, 0);
    try {
      isEnabled = a.getBoolean(0, true);
    } catch (Exception ignore) {
    }
    a.recycle();
    view.setEnabled(isEnabled);
  }

  protected void supportTextView(Context context, TextView view, AttributeSet attrs) {
    if (view == null) {
      return;
    }
    supportTypeface(context, view, attrs);
    supportVectorDrawable(context, view, attrs);
  }

  protected void supportTypeface(Context context, TextView view, AttributeSet attrs) {
    if (view == null) {
      return;
    }
    Typeface typeface = Typography.getTypeface(context, attrs, view instanceof Button ? R.attr.buttonStyle : 0, 0);
    typeface = typeface != null ? typeface : Typography.getDefaultTypeface(context);
    if (typeface != null && view.getTypeface() != typeface) {
      view.setTypeface(typeface);
    }
  }

  protected void supportVectorDrawable(Context context, ImageView view, AttributeSet attrs) {
    Drawable srcCompat = MdVectorDrawableCompat.getFromAttribute(context, attrs, R.attr.srcCompat);
    if (srcCompat != null) {
      view.setImageDrawable(srcCompat);
    }
  }

  protected void supportVectorDrawable(Context context, TextView view, AttributeSet attrs) {
    Drawable[] drawables = MdVectorDrawableCompat.getFromAttribute(context, attrs,
        R.attr.drawableLeftCompat, R.attr.drawableTopCompat, R.attr.drawableRightCompat, R.attr.drawableBottomCompat,
        R.attr.drawableStartCompat, R.attr.drawableEndCompat);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      view.setCompoundDrawablesRelativeWithIntrinsicBounds(
          drawables[4] != null ? drawables[4] : drawables[0],
          drawables[1],
          drawables[5] != null ? drawables[5] : drawables[2],
          drawables[3]
      );
    } else {
      view.setCompoundDrawablesWithIntrinsicBounds(
          drawables[4] != null ? drawables[4] : drawables[0],
          drawables[1],
          drawables[5] != null ? drawables[5] : drawables[2],
          drawables[3]
      );
    }
  }
}
