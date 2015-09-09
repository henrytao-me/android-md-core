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

package me.henrytao.mdcore.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by henrytao on 5/10/15.
 */
public class ResourceUtils {

  public static void closeKeyBoard(Activity activity) {
    closeKeyBoard(activity, activity.getCurrentFocus());
  }

  public static void closeKeyBoard(Context context, View view) {
    if (view != null) {
      ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
          .hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
  }

  public static int getColorFromAttribute(Activity activity, int attrId) {
    if (attrId == 0) {
      return 0;
    }
    TypedValue typedValue = new TypedValue();
    activity.getTheme().resolveAttribute(attrId, typedValue, true);
    return typedValue.data;
  }

  public static int getDimensionPixelSizeFromAttribute(Activity activity, int attrId) {
    if (attrId == 0) {
      return 0;
    }
    TypedValue typedValue = new TypedValue();
    activity.getTheme().resolveAttribute(attrId, typedValue, true);
    DisplayMetrics metrics = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
    return (int) typedValue.getDimension(metrics);
  }

  public static int getDrawableIdFromAttribute(Context context, int attrId) {
    if (attrId == 0) {
      return 0;
    }
    TypedValue typedValue = new TypedValue();
    context.getTheme().resolveAttribute(attrId, typedValue, true);
    return typedValue.resourceId;
  }

  public static View inflate(Activity activity, int layoutResId) {
    return inflate(activity, layoutResId, null, false);
  }

  public static View inflate(Activity activity, int layoutResId, ViewGroup parent) {
    return inflate(activity, layoutResId, parent, false);
  }

  public static View inflate(Activity activity, int layoutResId, ViewGroup root, boolean attachToRoot) {
    return activity.getLayoutInflater().inflate(layoutResId, root, attachToRoot);
  }

  public static void moveCursorToTheEnd(EditText editText) {
    int length = editText.getText().length();
    editText.setSelection(length, length);
  }

  public static void showKeyboard(Context context, EditText editText) {
    editText.requestFocus();
    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
  }
}
