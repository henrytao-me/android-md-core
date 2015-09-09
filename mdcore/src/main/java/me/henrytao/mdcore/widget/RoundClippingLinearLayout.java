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

package me.henrytao.mdcore.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import me.henrytao.mdcore.R;

/**
 * Created by henrytao on 5/31/15.
 */
public class RoundClippingLinearLayout extends LinearLayout {

  private int mBackgroundColor;

  private RectF mBounds;

  private int mCornerRadius;

  private Paint mDrawPaint;

  public RoundClippingLinearLayout(Context context) {
    super(context);
    init(context, null);
  }

  public RoundClippingLinearLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  public RoundClippingLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public RoundClippingLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context, attrs);
  }

  @Override
  protected void dispatchDraw(Canvas canvas) {
    canvas.drawRoundRect(mBounds, mCornerRadius, mCornerRadius, mDrawPaint);
    super.dispatchDraw(canvas);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    if (w != oldw && h != oldh) {
      mBounds = new RectF(0, 0, w, h);
    }
  }

  protected void init(Context context, AttributeSet attrs) {
    mBackgroundColor = Color.TRANSPARENT;
    mCornerRadius = 0;
    if (attrs != null) {
      TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundClippingLinearLayout, 0, 0);
      mBackgroundColor = a.getColor(R.styleable.RoundClippingLinearLayout_md_background_color, Color.TRANSPARENT);
      mCornerRadius = a.getDimensionPixelSize(R.styleable.RoundClippingLinearLayout_md_corner_radius, 0);
      a.recycle();
    }

    mBounds = new RectF();
    mBounds.set(0, 0, getMeasuredWidth(), getMeasuredHeight());

    mDrawPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mDrawPaint.setColor(mBackgroundColor);
    mDrawPaint.setStyle(Paint.Style.FILL);
  }
}
