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

import android.app.Activity;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

import me.henrytao.mddemo.R;

/**
 * Created by henrytao on 10/18/15.
 */
public class ThemeUtils {

  public static int DEFAULT_THEME_RES_ID = R.style.AppTheme;

  private static int sCurrentThemeResId = DEFAULT_THEME_RES_ID;

  private static Map<Integer, Integer> sThemes = new HashMap<>();

  static {
    sThemes.put(R.id.action_theme_red, R.style.AppTheme_Red);
    sThemes.put(R.id.action_theme_pink, R.style.AppTheme_Pink);
    sThemes.put(R.id.action_theme_purple, R.style.AppTheme_Purple);
    sThemes.put(R.id.action_theme_deep_purple, R.style.AppTheme_DeepPurple);
    sThemes.put(R.id.action_theme_indigo, R.style.AppTheme_Indigo);
    sThemes.put(R.id.action_theme_blue, R.style.AppTheme_Blue);
    sThemes.put(R.id.action_theme_light_blue, R.style.AppTheme_LightBlue);
    sThemes.put(R.id.action_theme_cyan, R.style.AppTheme_Cyan);
    sThemes.put(R.id.action_theme_teal, R.style.AppTheme_Teal);
    sThemes.put(R.id.action_theme_green, R.style.AppTheme_Green);
    sThemes.put(R.id.action_theme_light_green, R.style.AppTheme_LightGreen);
    sThemes.put(R.id.action_theme_lime, R.style.AppTheme_Lime);
    sThemes.put(R.id.action_theme_yellow, R.style.AppTheme_Yellow);
    sThemes.put(R.id.action_theme_amber, R.style.AppTheme_Amber);
    sThemes.put(R.id.action_theme_orange, R.style.AppTheme_Orange);
    sThemes.put(R.id.action_theme_deep_orange, R.style.AppTheme_DeepOrange);
    sThemes.put(R.id.action_theme_brown, R.style.AppTheme_Brown);
    sThemes.put(R.id.action_theme_grey, R.style.AppTheme_Grey);
    sThemes.put(R.id.action_theme_blue_grey, R.style.AppTheme_BlueGrey);
    sThemes.put(R.id.action_theme_white, R.style.AppTheme_White);
    sThemes.put(R.id.action_theme_black, R.style.AppTheme_Black);
  }

  public static void changeToTheme(Activity activity, int themeId) {
    int themeResId = sThemes.containsKey(themeId) ? sThemes.get(themeId) : 0;
    if (themeResId > 0 && themeResId != sCurrentThemeResId) {
      sCurrentThemeResId = themeResId;
      Intent intent = new Intent(activity, activity.getClass());
      intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      activity.startActivity(intent);
      activity.finish();
    }
  }

  public static void onCreate(Activity activity) {
    activity.setTheme(sCurrentThemeResId);
  }
}
