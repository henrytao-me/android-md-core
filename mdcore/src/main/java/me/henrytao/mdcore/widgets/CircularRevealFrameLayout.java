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

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by henrytao on 8/16/16.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CircularRevealFrameLayout extends FrameLayout {

  private float mCenterX;

  private float mCenterY;

  private boolean mIsClipOutlines;

  private boolean mIsShouldNotClipOutlines;

  private float mRadius;

  private Path mRevealPath;

  public CircularRevealFrameLayout(Context context) {
    super(context);
    init();
  }

  public CircularRevealFrameLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public CircularRevealFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public CircularRevealFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  public void draw(Canvas canvas) {
    if (!mIsClipOutlines || mIsShouldNotClipOutlines) {
      super.draw(canvas);
      return;
    }
    try {
      final int state = canvas.save();
      mRevealPath.reset();
      mRevealPath.addCircle(mCenterX, mCenterY, mRadius, Path.Direction.CW);
      canvas.clipPath(mRevealPath);
      super.draw(canvas);
      canvas.restoreToCount(state);
    } catch (Exception ignore) {
      mIsShouldNotClipOutlines = true;
    }
  }

  public void setClipCenter(int x, int y) {
    mCenterX = x;
    mCenterY = y;
  }

  public void setClipOutLines(boolean isClipOutlines) {
    mIsClipOutlines = isClipOutlines;
  }

  public void setRadius(float radius) {
    mRadius = radius;
    invalidate();
  }

  private void init() {
    mRevealPath = new Path();
    mIsClipOutlines = false;
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
      // clipPath only available on hardware for 18+
      setLayerType(LAYER_TYPE_SOFTWARE, null);
    }
    setWillNotDraw(false);
  }
}
