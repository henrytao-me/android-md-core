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

package me.henrytao.mddemo.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import me.henrytao.mdcore.utils.AlertDialogBuilder;
import me.henrytao.mddemo.R;

/**
 * Created by henrytao on 9/8/15.
 */
public class DialogUtils {

  public static AlertDialog showConfirmDialog(Context context, CharSequence message, int positiveTextId, int negativeTextId,
      DialogInterface.OnClickListener onPositiveClickListener, DialogInterface.OnClickListener onNegativeClickListener) {
    AlertDialog alertDialog = new AlertDialogBuilder(context)
        .setMessage(message)
        .setPositiveButton(positiveTextId, onPositiveClickListener)
        .setNegativeButton(negativeTextId, onNegativeClickListener)
        .create();
    alertDialog.show();
    return alertDialog;
  }

  public static AlertDialog showConfirmDialog(Context context, int messageId, int positiveTextId, int negativeTextId,
      DialogInterface.OnClickListener onPositiveClickListener, DialogInterface.OnClickListener onNegativeClickListener) {
    return showConfirmDialog(context, context.getString(messageId), positiveTextId, negativeTextId,
        onPositiveClickListener, onNegativeClickListener);
  }

  public static AlertDialog showConfirmDialog(Context context, CharSequence message,
      DialogInterface.OnClickListener onPositiveClickListener, DialogInterface.OnClickListener onNegativeClickListener) {
    return showConfirmDialog(context, message, R.string.text_ok, R.string.text_cancel, onPositiveClickListener, onNegativeClickListener);
  }

  public static AlertDialog showConfirmDialog(Context context, int messageId,
      DialogInterface.OnClickListener onPositiveClickListener, DialogInterface.OnClickListener onNegativeClickListener) {
    return showConfirmDialog(context, messageId, R.string.text_ok, R.string.text_cancel, onPositiveClickListener, onNegativeClickListener);
  }

  public static AlertDialog showInfoDialog(Context context, int messageId, DialogInterface.OnClickListener onClickListener,
      DialogInterface.OnDismissListener onDismissListener) {
    return showInfoDialog(context, context.getString(messageId), onClickListener, onDismissListener);
  }

  public static AlertDialog showInfoDialog(Context context, CharSequence message, DialogInterface.OnClickListener onClickListener,
      DialogInterface.OnDismissListener onDismissListener) {
    AlertDialog alertDialog = new AlertDialogBuilder(context)
        .setTitle("sample")
        .setMessage(message)
        .setPositiveButton(R.string.text_ok, onClickListener)
        .setOnDismissListener(onDismissListener)
        .create();
    alertDialog.show();
    return alertDialog;
  }

  public static AlertDialog showInfoDialog(Context context, int titleId, int messageId, DialogInterface.OnClickListener onClickListener,
      DialogInterface.OnDismissListener onDismissListener) {
    AlertDialog alertDialog = new AlertDialogBuilder(context)
        .setTitle(titleId)
        .setMessage(messageId)
        .setPositiveButton(R.string.text_ok, onClickListener)
        .setOnDismissListener(onDismissListener)
        .create();
    alertDialog.show();
    return alertDialog;
  }
}
