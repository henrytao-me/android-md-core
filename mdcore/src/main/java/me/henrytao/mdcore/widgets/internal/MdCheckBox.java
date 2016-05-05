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

package me.henrytao.mdcore.widgets.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.ViewUtils;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import me.henrytao.mdcore.R;

/**
 * Created by henrytao on 5/3/16.
 */
public class MdCheckBox extends AppCompatCheckBox {

  private static int getDefStyleAttr(Context context, AttributeSet attrs) {
    return hasCustomDrawable(context, attrs) ? R.attr.MdIconToggleStyle : R.attr.MdCheckBoxStyle;
  }

  private static boolean hasCustomDrawable(Context context, AttributeSet attrs) {
    TypedArray a = context.getTheme().obtainStyledAttributes(attrs, new int[]{
        R.attr.srcCompat
    }, 0, 0);
    boolean result = a.getResourceId(0, 0) > 0;
    a.recycle();
    return result;
  }

  private int mDrawablePadding;

  private boolean mHasCustomDrawable;

  private int mMinWidth;

  private int mPaddingBottom;

  private int mPaddingLeft;

  private int mPaddingRight;

  private int mPaddingTop;

  public MdCheckBox(Context context) {
    this(context, null);
  }

  public MdCheckBox(Context context, AttributeSet attrs) {
    this(context, attrs, getDefStyleAttr(context, attrs));
  }

  public MdCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr > 0 ? defStyleAttr : getDefStyleAttr(context, attrs));
    initFromAttributes(attrs);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if (isLayoutRtl()) {
      canvas.translate(-mPaddingRight, 0);
    } else {
      canvas.translate(mPaddingLeft, 0);
    }
    super.onDraw(canvas);
    Drawable background = getBackground();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && background != null) {
      boolean isLayoutRtl = isLayoutRtl();
      Rect bounds = background.getBounds();
      int top = bounds.top;
      int bottom = bounds.bottom;
      int left = (isLayoutRtl ? getWidth() - mMinWidth : 0) + (!mHasCustomDrawable && !isLayoutRtl ? mPaddingLeft : 0);
      int right = (isLayoutRtl ? getWidth() : mMinWidth) - (!mHasCustomDrawable && isLayoutRtl ? mPaddingRight : 0);
      background.setHotspotBounds(left, top, right, bottom);
    }
  }

  protected void initFromAttributes(AttributeSet attrs) {
    mHasCustomDrawable = hasCustomDrawable(getContext(), attrs);

    mPaddingLeft = getPaddingLeft();
    mPaddingTop = getPaddingTop();
    mPaddingRight = getPaddingRight();
    mPaddingBottom = getPaddingBottom();
    mDrawablePadding = getCompoundDrawablePadding();
    mMinWidth = ViewCompat.getMinimumWidth(this);

    invalidatePadding();
    addTextChangedListener(new TextWatcher() {
      @Override
      public void afterTextChanged(Editable s) {
        invalidatePadding();
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }
    });
  }

  private void invalidatePadding() {
    boolean isLayoutRtl = isLayoutRtl();
    int adjustedLeft = TextUtils.isEmpty(getText()) ? 0 : mDrawablePadding;
    int adjustedRight = mPaddingLeft + mPaddingRight;
    setPadding(!isLayoutRtl ? adjustedLeft : adjustedRight, mPaddingTop, !isLayoutRtl ? adjustedRight : adjustedLeft, mPaddingBottom);
  }

  private boolean isLayoutRtl() {
    return ViewUtils.isLayoutRtl(this);
  }
}
