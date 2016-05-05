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

package me.henrytao.mdcore.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * Created by henrytao on 5/3/16.
 */
public class MdCheckBox extends AppCompatCheckBox {

  private int mDrawablePadding;

  private int mPaddingBottom;

  private int mPaddingLeft;

  private int mPaddingRight;

  private int mPaddingTop;

  public MdCheckBox(Context context) {
    super(context);
    initFromAttributes(null, 0);
  }

  public MdCheckBox(Context context, AttributeSet attrs) {
    super(context, attrs);
    initFromAttributes(attrs, 0);
  }

  public MdCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initFromAttributes(attrs, defStyleAttr);
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
      Rect bounds = background.getBounds();
      int size = Math.abs(bounds.bottom - bounds.top);
      int top = bounds.top;
      int bottom = bounds.bottom;
      int left = isLayoutRtl() ? getWidth() - size : 0;
      int right = isLayoutRtl() ? getWidth() : size;
      background.setHotspotBounds(left, top, right, bottom);
    }
  }

  @Override
  protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
    super.onTextChanged(text, start, lengthBefore, lengthAfter);
    invalidatePadding();
  }

  protected void initFromAttributes(AttributeSet attrs, int defStyleAttr) {
    mPaddingLeft = getPaddingLeft();
    mPaddingTop = getPaddingTop();
    mPaddingRight = getPaddingRight();
    mPaddingBottom = getPaddingBottom();
    mDrawablePadding = getCompoundDrawablePadding();
    invalidatePadding();
  }

  private void invalidatePadding() {
    if (isLayoutRtl()) {
      setPadding(mPaddingLeft + mPaddingRight, mPaddingTop, TextUtils.isEmpty(getText()) ? 0 : mDrawablePadding, mPaddingBottom);
    } else {
      setPadding(TextUtils.isEmpty(getText()) ? 0 : mDrawablePadding, mPaddingTop, mPaddingRight + mPaddingLeft, mPaddingBottom);
    }
  }

  private boolean isLayoutRtl() {
    return ViewUtils.isLayoutRtl(this);
  }
}
