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

package me.henrytao.mddemo.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

/**
 * Created by henrytao on 10/24/15.
 */
public class SharePreferenceUtils {

  private static Application sApplication;

  public static SharedPreferences getInstance() {
    return sApplication.getSharedPreferences(getPreferenceKey(), Context.MODE_PRIVATE);
  }

  public static void init(Application application) {
    sApplication = application;
  }

  protected static String getPreferenceKey() {
    return String.format(Locale.US, "%s.preferences.application", sApplication.getPackageName());
  }
}
