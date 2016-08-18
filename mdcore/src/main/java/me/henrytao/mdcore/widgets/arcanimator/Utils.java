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

import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * Created by henrytao on 8/15/16.
 */
class Utils {

  public static float acos(double value) {
    return (float) Math.toDegrees(Math.acos(value));
  }

  public static float asin(double value) {
    return (float) Math.toDegrees(Math.asin(value));
  }

  public static float centerX(View view) {
    return ViewCompat.getX(view) + view.getMeasuredWidth() / 2;
  }

  public static float centerY(View view) {
    return ViewCompat.getY(view) + view.getMeasuredHeight() / 2;
  }

  public static float cos(double degree) {
    return (float) Math.cos(Math.toRadians(degree));
  }

  public static float sin(double degree) {
    return (float) Math.sin(Math.toRadians(degree));
  }
}
