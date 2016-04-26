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

package me.henrytao.mdcore.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;

/**
 * Created by henrytao on 5/10/15.
 */
public class AlertDialogBuilder extends AlertDialog.Builder {

  protected boolean mAutoDismiss = true;

  protected CharSequence mNegativeButtonText;

  protected DialogInterface.OnClickListener mNegativeOnClickListener;

  protected CharSequence mNeutralButtonText;

  protected DialogInterface.OnClickListener mNeutralOnClickListener;

  protected DialogInterface.OnShowListener mOnShowListener;

  protected CharSequence mPositiveButtonText;

  protected DialogInterface.OnClickListener mPositiveOnClickListener;

  private DialogInterface.OnClickListener mBlankOnClickListener = new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
  };

  public AlertDialogBuilder(Context context) {
    super(context);
  }

  public AlertDialogBuilder(Context context, int theme) {
    super(context, theme);
  }

  @Override
  public AlertDialog create() {
    final AlertDialog alertDialog = super.create();
    alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
      @Override
      public void onShow(DialogInterface dialog) {
        if (!mAutoDismiss) {
          Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
          Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
          Button neutralButton = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
          positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (mPositiveOnClickListener != null) {
                mPositiveOnClickListener.onClick(alertDialog, DialogInterface.BUTTON_POSITIVE);
              }
            }
          });
          negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (mNegativeOnClickListener != null) {
                mNegativeOnClickListener.onClick(alertDialog, DialogInterface.BUTTON_NEGATIVE);
              }
            }
          });
          neutralButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (mNeutralOnClickListener != null) {
                mNeutralOnClickListener.onClick(alertDialog, DialogInterface.BUTTON_NEUTRAL);
              }
            }
          });
        }
        if (mOnShowListener != null) {
          mOnShowListener.onShow(dialog);
        }
      }
    });
    return alertDialog;
  }

  @Override
  public Context getContext() {
    return super.getContext();
  }

  @Override
  public AlertDialogBuilder setAdapter(ListAdapter adapter, DialogInterface.OnClickListener listener) {
    super.setAdapter(adapter, listener);
    return this;
  }

  @Override
  public AlertDialogBuilder setCancelable(boolean cancelable) {
    super.setCancelable(cancelable);
    return this;
  }

  @Override
  public AlertDialogBuilder setCursor(Cursor cursor, DialogInterface.OnClickListener listener, String labelColumn) {
    super.setCursor(cursor, listener, labelColumn);
    return this;
  }

  @Override
  public AlertDialogBuilder setCustomTitle(View customTitleView) {
    super.setCustomTitle(customTitleView);
    return this;
  }

  @Override
  public AlertDialogBuilder setIcon(int iconId) {
    super.setIcon(iconId);
    return this;
  }

  @Override
  public AlertDialogBuilder setIcon(Drawable icon) {
    super.setIcon(icon);
    return this;
  }

  @Override
  public AlertDialogBuilder setIconAttribute(int attrId) {
    super.setIconAttribute(attrId);
    return this;
  }

  @Override
  public AlertDialogBuilder setInverseBackgroundForced(boolean useInverseBackground) {
    super.setInverseBackgroundForced(useInverseBackground);
    return this;
  }

  @Override
  public AlertDialogBuilder setItems(int itemsId, DialogInterface.OnClickListener listener) {
    super.setItems(itemsId, listener);
    return this;
  }

  @Override
  public AlertDialogBuilder setItems(CharSequence[] items, DialogInterface.OnClickListener listener) {
    super.setItems(items, listener);
    return this;
  }

  @Override
  public AlertDialogBuilder setMessage(int messageId) {
    super.setMessage(messageId);
    return this;
  }

  @Override
  public AlertDialogBuilder setMessage(CharSequence message) {
    super.setMessage(message);
    return this;
  }

  @Override
  public AlertDialogBuilder setMultiChoiceItems(int itemsId, boolean[] checkedItems, DialogInterface.OnMultiChoiceClickListener listener) {
    super.setMultiChoiceItems(itemsId, checkedItems, listener);
    return this;
  }

  @Override
  public AlertDialogBuilder setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems,
      DialogInterface.OnMultiChoiceClickListener listener) {
    super.setMultiChoiceItems(items, checkedItems, listener);
    return this;
  }

  @Override
  public AlertDialogBuilder setMultiChoiceItems(Cursor cursor, String isCheckedColumn, String labelColumn,
      DialogInterface.OnMultiChoiceClickListener listener) {
    super.setMultiChoiceItems(cursor, isCheckedColumn, labelColumn, listener);
    return this;
  }

  @Override
  public AlertDialogBuilder setNegativeButton(int textId, DialogInterface.OnClickListener listener) {
    super.setNegativeButton(textId, listener);
    mNegativeButtonText = getContext().getText(textId);
    mNegativeOnClickListener = listener;
    return this;
  }

  @Override
  public AlertDialogBuilder setNegativeButton(CharSequence text, DialogInterface.OnClickListener listener) {
    super.setNegativeButton(text, listener);
    mNegativeButtonText = text;
    mNegativeOnClickListener = listener;
    return this;
  }

  @Override
  public AlertDialogBuilder setNeutralButton(int textId, DialogInterface.OnClickListener listener) {
    super.setNeutralButton(textId, listener);
    mNeutralButtonText = getContext().getText(textId);
    mNeutralOnClickListener = listener;
    return this;
  }

  @Override
  public AlertDialogBuilder setNeutralButton(CharSequence text, DialogInterface.OnClickListener listener) {
    super.setNeutralButton(text, listener);
    mNeutralButtonText = text;
    mNeutralOnClickListener = listener;
    return this;
  }

  @Override
  public AlertDialogBuilder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
    super.setOnCancelListener(onCancelListener);
    return this;
  }

  @Override
  public AlertDialogBuilder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
    super.setOnDismissListener(onDismissListener);
    return this;
  }

  @Override
  public AlertDialogBuilder setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
    super.setOnItemSelectedListener(listener);
    return this;
  }

  @Override
  public AlertDialogBuilder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
    super.setOnKeyListener(onKeyListener);
    return this;
  }

  @Override
  public AlertDialogBuilder setPositiveButton(int textId, DialogInterface.OnClickListener listener) {
    super.setPositiveButton(textId, listener);
    mPositiveButtonText = getContext().getText(textId);
    mPositiveOnClickListener = listener;
    return this;
  }

  @Override
  public AlertDialogBuilder setPositiveButton(CharSequence text, DialogInterface.OnClickListener listener) {
    super.setPositiveButton(text, listener);
    mPositiveButtonText = text;
    mPositiveOnClickListener = listener;
    return this;
  }

  @Override
  public AlertDialogBuilder setRecycleOnMeasureEnabled(boolean enabled) {
    super.setRecycleOnMeasureEnabled(enabled);
    return this;
  }

  @Override
  public AlertDialogBuilder setSingleChoiceItems(int itemsId, int checkedItem, DialogInterface.OnClickListener listener) {
    super.setSingleChoiceItems(itemsId, checkedItem, listener);
    return this;
  }

  @Override
  public AlertDialogBuilder setSingleChoiceItems(Cursor cursor, int checkedItem, String labelColumn,
      DialogInterface.OnClickListener listener) {
    super.setSingleChoiceItems(cursor, checkedItem, labelColumn, listener);
    return this;
  }

  @Override
  public AlertDialogBuilder setSingleChoiceItems(CharSequence[] items, int checkedItem, DialogInterface.OnClickListener listener) {
    super.setSingleChoiceItems(items, checkedItem, listener);
    return this;
  }

  @Override
  public AlertDialogBuilder setSingleChoiceItems(ListAdapter adapter, int checkedItem, DialogInterface.OnClickListener listener) {
    super.setSingleChoiceItems(adapter, checkedItem, listener);
    return this;
  }

  @Override
  public AlertDialogBuilder setTitle(int titleId) {
    super.setTitle(titleId);
    return this;
  }

  @Override
  public AlertDialogBuilder setTitle(CharSequence title) {
    super.setTitle(title);
    return this;
  }

  @Override
  public AlertDialogBuilder setView(int layoutResId) {
    super.setView(layoutResId);
    return this;
  }

  @Override
  public AlertDialogBuilder setView(View view) {
    super.setView(view);
    return this;
  }

  @Override
  public AlertDialogBuilder setView(View view, int viewSpacingLeft, int viewSpacingTop, int viewSpacingRight, int viewSpacingBottom) {
    super.setView(view, viewSpacingLeft, viewSpacingTop, viewSpacingRight, viewSpacingBottom);
    return this;
  }

  @Override
  public AlertDialog show() {
    return super.show();
  }

  public AlertDialogBuilder setAutoDismiss(boolean autoDismiss) {
    mAutoDismiss = autoDismiss;
    return this;
  }

  public AlertDialogBuilder setNegativeButton(int textId) {
    setNegativeButton(textId, mBlankOnClickListener);
    return this;
  }

  public AlertDialogBuilder setNegativeButton(CharSequence text) {
    setNegativeButton(text, mBlankOnClickListener);
    return this;
  }

  public AlertDialogBuilder setNeutralButton(int textId) {
    setNeutralButton(textId, mBlankOnClickListener);
    return this;
  }

  public AlertDialogBuilder setNeutralButton(CharSequence text) {
    setNeutralButton(text, mBlankOnClickListener);
    return this;
  }

  public AlertDialogBuilder setOnNegativeClickListener(DialogInterface.OnClickListener negativeOnClickListener) {
    mNegativeOnClickListener = negativeOnClickListener;
    setNegativeButton(TextUtils.isEmpty(mNegativeButtonText) ? "" : mNegativeButtonText, negativeOnClickListener);
    return this;
  }

  public AlertDialogBuilder setOnNeutralClickListener(DialogInterface.OnClickListener neutralOnClickListener) {
    mNeutralOnClickListener = neutralOnClickListener;
    setNeutralButton(TextUtils.isEmpty(mNeutralButtonText) ? "" : mNeutralButtonText, neutralOnClickListener);
    return this;
  }

  public AlertDialogBuilder setOnPositiveClickListener(DialogInterface.OnClickListener positiveOnClickListener) {
    mPositiveOnClickListener = positiveOnClickListener;
    setPositiveButton(TextUtils.isEmpty(mPositiveButtonText) ? "" : mPositiveButtonText, positiveOnClickListener);
    return this;
  }

  public AlertDialogBuilder setOnShowListener(DialogInterface.OnShowListener onShowListener) {
    mOnShowListener = onShowListener;
    return this;
  }

  public AlertDialogBuilder setPositiveButton(CharSequence text) {
    setPositiveButton(text, mBlankOnClickListener);
    return this;
  }

  public AlertDialogBuilder setPositiveButton(int textId) {
    setPositiveButton(textId, mBlankOnClickListener);
    return this;
  }
}
