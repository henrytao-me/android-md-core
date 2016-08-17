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

package me.henrytao.mdcore.widgets.arcanimator;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by henrytao on 8/15/16.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ArcAnimator extends ValueAnimator {

  public static ArcAnimator create(View target, float endX, float endY, float degree, Side side) {
    ArcMetric arcMetric = ArcMetric.evaluate(Utils.centerX(target), Utils.centerY(target), endX, endY, degree, side);
    return new ArcAnimator(arcMetric, target);
  }

  protected ArcAnimator(ArcMetric arcMetric, View target) {
    setFloatValues(arcMetric.getStartDegree(), arcMetric.getEndDegree());
    addUpdateListener(new AnimatorUpdateListener(arcMetric, target));
  }

  private static class AnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {

    private final ArcMetric mArcMetric;

    private final WeakReference<View> mTarget;

    public AnimatorUpdateListener(ArcMetric arcMetric, View target) {
      mArcMetric = arcMetric;
      mTarget = new WeakReference<>(target);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
      View target = mTarget.get();
      if (target != null) {
        float degree = (float) animation.getAnimatedValue();
        float x = mArcMetric.getAxisPoint().x + mArcMetric.mRadius * Utils.cos(degree);
        float y = mArcMetric.getAxisPoint().y - mArcMetric.mRadius * Utils.sin(degree);
        ViewCompat.setX(target, x - target.getWidth() / 2);
        ViewCompat.setY(target, y - target.getHeight() / 2);
      }
    }
  }
}