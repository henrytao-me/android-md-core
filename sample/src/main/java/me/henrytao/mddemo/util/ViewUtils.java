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

package me.henrytao.mddemo.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by henrytao on 5/10/15.
 */
public class ViewUtils {

  public static void closeKeyBoard(Activity activity) {
    closeKeyBoard(activity, activity.getCurrentFocus());
  }

  public static void closeKeyBoard(Context context, View view) {
    if (view != null) {
      ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
          .hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
  }

  public static View inflate(Context context, int layoutResId) {
    return inflate(context, layoutResId, null, false);
  }

  public static View inflate(Context context, int layoutResId, ViewGroup parent) {
    return inflate(context, layoutResId, parent, false);
  }

  public static View inflate(Context context, int layoutResId, ViewGroup root, boolean attachToRoot) {
    if (context instanceof Activity) {
      return ((Activity) context).getLayoutInflater().inflate(layoutResId, root, attachToRoot);
    }
    return null;
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
