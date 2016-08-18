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
import android.support.annotation.FloatRange;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by henrytao on 8/15/16.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AlphaAnimator extends ValueAnimator {

  public static AlphaAnimator create(View target, @FloatRange(from = 0.0f, to = 1.0f) float fromAlpha,
      @FloatRange(from = 0.0f, to = 1.0f) float toAlpha) {
    return new AlphaAnimator(target, fromAlpha, toAlpha);
  }

  private final float mFromAlpha;

  private final WeakReference<View> mTarget;

  private final float mToAlpha;

  protected AlphaAnimator(View target, float fromAlpha, float toAlpha) {
    mTarget = new WeakReference<>(target);
    mFromAlpha = fromAlpha;
    mToAlpha = toAlpha;
    setFloatValues(fromAlpha, toAlpha);
    addUpdateListener(new AnimatorUpdateListener(target));
  }

  @Override
  public void start() {
    View target = mTarget.get();
    if (target != null) {
      target.setAlpha(mFromAlpha);
    }
    super.start();
  }

  private static class AnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {

    private final WeakReference<View> mTarget;

    public AnimatorUpdateListener(View target) {
      mTarget = new WeakReference<>(target);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
      View target = mTarget.get();
      if (target != null) {
        float alpha = (float) animation.getAnimatedValue();
        target.setAlpha(alpha);
      }
    }
  }
}