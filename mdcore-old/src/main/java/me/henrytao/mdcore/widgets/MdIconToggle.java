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

package me.henrytao.mdcore.widgets;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.ViewUtils;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import java.io.IOException;

import me.henrytao.mdcore.R;
import me.henrytao.mdcore.utils.ResourceUtils;

/**
 * Created by henrytao on 10/26/15.
 */
public class MdIconToggle extends AppCompatCheckBox {

  private int mDrawablePadding;

  private int mPaddingBottom;

  private int mPaddingLeft;

  private int mPaddingRight;

  private int mPaddingTop;

  public MdIconToggle(Context context) {
    this(context, null);
  }

  public MdIconToggle(Context context, AttributeSet attrs) {
    this(context, attrs, R.attr.MdIconToggleStyle);
  }

  public MdIconToggle(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initFromAttributes(attrs, defStyleAttr > 0 ? defStyleAttr : R.attr.MdIconToggleStyle);
  }

  @Override
  public void setButtonDrawable(Drawable buttonDrawable) {
    super.setButtonDrawable(buttonDrawable);
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

  protected void initFromAttributes(AttributeSet attrs, int defStyleAttr) {
    Context context = getContext();

    mPaddingLeft = getPaddingLeft();
    mPaddingTop = getPaddingTop();
    mPaddingRight = getPaddingRight();
    mPaddingBottom = getPaddingBottom();
    mDrawablePadding = getCompoundDrawablePadding();
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

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CompoundButton, defStyleAttr, 0);
      int buttonTintId = 0;
      try {
        buttonTintId = a.getResourceId(R.styleable.CompoundButton_buttonTint, 0);
      } finally {
        a.recycle();
      }
      if (buttonTintId > 0) {
        try {
          setSupportButtonTintList(ResourceUtils.createColorStateListFromResId(context, buttonTintId));
        } catch (XmlPullParserException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
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
