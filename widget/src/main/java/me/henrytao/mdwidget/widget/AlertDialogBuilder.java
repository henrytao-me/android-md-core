/*
 * Copyright (C) 2015 MySQUAR. All rights reserved.
 *
 * This software is the confidential and proprietary information of MySQUAR or one of its
 * subsidiaries. You shall not disclose this confidential information and shall use it only in
 * accordance with the terms of the license agreement or other applicable agreement you entered into
 * with MySQUAR.
 *
 * MySQUAR MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. MySQUAR SHALL NOT BE LIABLE FOR ANY LOSSES
 * OR DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR
 * ITS DERIVATIVES.
 */

package me.henrytao.mdwidget.widget;

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

  private AlertDialog mDialog;

  public AlertDialogBuilder(Context context) {
    super(context);
  }

  public AlertDialogBuilder(Context context, int theme) {
    super(context, theme);
  }

  @Override
  public AlertDialog create() {
    return super.create();
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
    mDialog = create();
    if (mOnShowListener != null) {
      mDialog.setOnShowListener(mOnShowListener);
    }
    mDialog.show();
    if (mAutoDismiss == false) {
      Button positiveButton = mDialog.getButton(DialogInterface.BUTTON_POSITIVE);
      Button negativeButton = mDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
      Button neutralButton = mDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
      positiveButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (mPositiveOnClickListener != null) {
            mPositiveOnClickListener.onClick(mDialog, DialogInterface.BUTTON_POSITIVE);
          }
        }
      });
      negativeButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (mNegativeOnClickListener != null) {
            mNegativeOnClickListener.onClick(mDialog, DialogInterface.BUTTON_NEGATIVE);
          }
        }
      });
      neutralButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (mNeutralOnClickListener != null) {
            mNeutralOnClickListener.onClick(mDialog, DialogInterface.BUTTON_NEUTRAL);
          }
        }
      });
    }
    return mDialog;
  }

  public AlertDialog getDialog() {
    return mDialog;
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
