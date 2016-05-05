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
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import me.henrytao.mdcore.R;
import me.henrytao.mdcore.utils.Ln;
import me.henrytao.mdcore.widgets.MdCheckBox;

/**
 * Created by henrytao on 4/27/16.
 */
public class MdLayoutInflaterFactory implements LayoutInflaterFactory {

  private static final String SUPPORT_BUTTON = "Button";

  private static final String SUPPORT_CHECK_BOX = "CheckBox";

  private final AppCompatDelegate mDelegate;

  public MdLayoutInflaterFactory(@NonNull AppCompatDelegate delegate) {
    mDelegate = delegate;
  }

  @Override
  public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
    Ln.d("custom | %s | %s", name, attrs.getClass().toString());
    switch (name) {
      //case SUPPORT_BUTTON:
      //  return new MdButton(context, attrs);
      case SUPPORT_CHECK_BOX:
        return new MdCheckBox(context, attrs);
    }

    // get view from appcompat
    View view = mDelegate.createView(parent, name, context, attrs);

    if (view instanceof ImageView) {
      supportImageView(context, (ImageView) view, attrs);
    } else if (view instanceof CheckBox) {
      supportCheckBox(context, (CheckBox) view, attrs);
    }
    return view;
  }

  private void supportButton(Context context, Button view, AttributeSet attrs) {

  }

  private void supportCheckBox(Context context, CheckBox view, AttributeSet attrs) {
    Drawable drawable = null;
    TypedArray a = context.getTheme().obtainStyledAttributes(attrs, new int[]{
        R.attr.srcCompat
    }, 0, 0);
    try {
      int resId = a.getResourceId(0, 0);
      if (resId > 0) {
        drawable = VectorDrawableCompat.create(context.getResources(), resId, context.getTheme());
      }
    } catch (Exception ignore) {
    }
    a.recycle();
    if (drawable != null) {
      view.setButtonDrawable(drawable);
    }
  }

  private void supportImageView(Context context, ImageView view, AttributeSet attrs) {
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
}
