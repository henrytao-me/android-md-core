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

package com.google.samples.apps.iosched.ui.widget;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

class SlidingTabStrip extends LinearLayout {

  private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 0x26;

  private static final int DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 0;

  private static final int DEFAULT_SELECTED_INDICATOR_COLOR = 0xFF33B5E5;

  private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 3;

  /**
   * Blend {@code color1} and {@code color2} using the given ratio.
   *
   * @param ratio of which to blend. 1.0 will return {@code color1}, 0.5 will give an even blend,
   *              0.0 will return {@code color2}.
   */
  private static int blendColors(int color1, int color2, float ratio) {
    final float inverseRation = 1f - ratio;
    float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
    float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
    float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
    return Color.rgb((int) r, (int) g, (int) b);
  }

  /**
   * Set the alpha value of the {@code color} to be the given {@code alpha} value.
   */
  private static int setColorAlpha(int color, byte alpha) {
    return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
  }

  private final Paint mBottomBorderPaint;

  private final int mBottomBorderThickness;

  private final int mDefaultBottomBorderColor;

  private final SimpleTabColorizer mDefaultTabColorizer;

  private final Paint mSelectedIndicatorPaint;

  private final int mSelectedIndicatorThickness;

  private SlidingTabLayout.TabColorizer mCustomTabColorizer;

  private int mSelectedPosition;

  private float mSelectionOffset;

  SlidingTabStrip(Context context) {
    this(context, null);
  }

  SlidingTabStrip(Context context, AttributeSet attrs) {
    super(context, attrs);
    setWillNotDraw(false);

    final float density = getResources().getDisplayMetrics().density;

    TypedValue outValue = new TypedValue();
    context.getTheme().resolveAttribute(R.attr.colorForeground, outValue, true);
    final int themeForegroundColor = outValue.data;

    mDefaultBottomBorderColor = setColorAlpha(themeForegroundColor,
        DEFAULT_BOTTOM_BORDER_COLOR_ALPHA);

    mDefaultTabColorizer = new SimpleTabColorizer();
    mDefaultTabColorizer.setIndicatorColors(DEFAULT_SELECTED_INDICATOR_COLOR);

    mBottomBorderThickness = (int) (DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS * density);
    mBottomBorderPaint = new Paint();
    mBottomBorderPaint.setColor(mDefaultBottomBorderColor);

    mSelectedIndicatorThickness = (int) (SELECTED_INDICATOR_THICKNESS_DIPS * density);
    mSelectedIndicatorPaint = new Paint();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    final int height = getHeight();
    final int childCount = getChildCount();
    final SlidingTabLayout.TabColorizer tabColorizer = mCustomTabColorizer != null
        ? mCustomTabColorizer
        : mDefaultTabColorizer;

    // Thick colored underline below the current selection
    if (childCount > 0) {
      View selectedTitle = getChildAt(mSelectedPosition);
      int left = selectedTitle.getLeft();
      int right = selectedTitle.getRight();
      int color = tabColorizer.getIndicatorColor(mSelectedPosition);

      if (mSelectionOffset > 0f && mSelectedPosition < (getChildCount() - 1)) {
        int nextColor = tabColorizer.getIndicatorColor(mSelectedPosition + 1);
        if (color != nextColor) {
          color = blendColors(nextColor, color, mSelectionOffset);
        }

        // Draw the selection partway between the tabs
        View nextTitle = getChildAt(mSelectedPosition + 1);
        left = (int) (mSelectionOffset * nextTitle.getLeft() +
            (1.0f - mSelectionOffset) * left);
        right = (int) (mSelectionOffset * nextTitle.getRight() +
            (1.0f - mSelectionOffset) * right);
      }

      mSelectedIndicatorPaint.setColor(color);

      canvas.drawRect(left, height - mSelectedIndicatorThickness, right,
          height, mSelectedIndicatorPaint);
    }

    // Thin underline along the entire bottom edge
    canvas.drawRect(0, height - mBottomBorderThickness, getWidth(), height, mBottomBorderPaint);
  }

  void onViewPagerPageChanged(int position, float positionOffset) {
    mSelectedPosition = position;
    mSelectionOffset = positionOffset;
    invalidate();
  }

  void setCustomTabColorizer(SlidingTabLayout.TabColorizer customTabColorizer) {
    mCustomTabColorizer = customTabColorizer;
    invalidate();
  }

  void setSelectedIndicatorColors(int... colors) {
    // Make sure that the custom colorizer is removed
    mCustomTabColorizer = null;
    mDefaultTabColorizer.setIndicatorColors(colors);
    invalidate();
  }

  private static class SimpleTabColorizer implements SlidingTabLayout.TabColorizer {

    private int[] mIndicatorColors;

    @Override
    public final int getIndicatorColor(int position) {
      return mIndicatorColors[position % mIndicatorColors.length];
    }

    void setIndicatorColors(int... colors) {
      mIndicatorColors = colors;
    }
  }
}
