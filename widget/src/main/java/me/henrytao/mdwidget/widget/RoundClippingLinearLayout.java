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

import me.henrytao.widget.R;

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
    if (attrs != null) {
      TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundClippingLinearLayout, 0, 0);
      mBackgroundColor = a.getColor(R.styleable.RoundClippingLinearLayout_rcll_background_color, Color.TRANSPARENT);
      mCornerRadius = a.getDimensionPixelSize(R.styleable.RoundClippingLinearLayout_rcll_corner_radius, 0);
      a.recycle();
    } else {
      mBackgroundColor = Color.TRANSPARENT;
      mCornerRadius = 0;
    }

    mBounds = new RectF();
    mBounds.set(0, 0, getMeasuredWidth(), getMeasuredHeight());

    mDrawPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mDrawPaint.setColor(mBackgroundColor);
    mDrawPaint.setStyle(Paint.Style.FILL);
  }
}
