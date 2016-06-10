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

package me.henrytao.mdcore.core;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.henrytao.mdcore.R;

/**
 * Created by henrytao on 10/10/15.
 */
public class MdCompat {

  public static Drawable createDrawableTint(Drawable drawable, int[] drawableState, ColorStateList tintList, PorterDuff.Mode tintMode) {
    if (drawable != null && (tintList != null || tintMode != null)) {
      drawable = DrawableCompat.wrap(drawable).mutate();
      if (tintList != null) {
        DrawableCompat.setTintList(drawable, tintList);
      }
      if (tintMode != null) {
        DrawableCompat.setTintMode(drawable, tintMode);
      }
      if (drawable.isStateful() && drawableState != null) {
        drawable.setState(drawableState);
      }
    }
    return drawable;
  }

  public static void enableTranslucentStatus(Activity activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = activity.getWindow();
      window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.setStatusBarColor(Color.TRANSPARENT);
    }
  }

  public static int getActionBarSize(Context context) {
    TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
    int size = (int) a.getDimension(0, 0);
    a.recycle();
    return size;
  }

  public static boolean getBooleanFromAttribute(Context context, int attrId) {
    if (attrId == 0) {
      return false;
    }
    TypedArray array = context.getTheme().obtainStyledAttributes(new int[]{R.attr.isLightTheme});
    return array.getBoolean(0, false);
  }

  public static int getColorFromAttribute(Context context, int attrId) {
    if (attrId == 0) {
      return 0;
    }
    TypedValue a = new TypedValue();
    context.getTheme().resolveAttribute(attrId, a, true);
    return ContextCompat.getColor(context, a.resourceId);
  }

  /**
   * A temporary fix for android.content.res.ColorStateList.inflate
   */
  public static ColorStateList getColorStateList(Context context, int resId) throws IOException, XmlPullParserException {
    XmlResourceParser parser = context.getResources().getXml(resId);
    AttributeSet attrs = Xml.asAttributeSet(parser);
    Resources r = context.getResources();

    final int innerDepth = parser.getDepth() + 2;
    int depth;
    int type;

    List<int[]> customStateList = new ArrayList<>();
    List<Integer> customColorList = new ArrayList<>();

    while ((type = parser.next()) != XmlPullParser.END_DOCUMENT
        && ((depth = parser.getDepth()) >= innerDepth || type != XmlPullParser.END_TAG)) {
      if (type != XmlPullParser.START_TAG || depth > innerDepth || !parser.getName().equals("item")) {
        continue;
      }
      // Parse all unrecognized attributes as state specifiers.
      int j = 0;
      final int numAttrs = attrs.getAttributeCount();
      int color = 0;
      float alpha = 1.0f;
      int[] stateSpec = new int[numAttrs];
      for (int i = 0; i < numAttrs; i++) {
        final int stateResId = attrs.getAttributeNameResource(i);
        switch (stateResId) {
          case android.R.attr.color:
            int colorAttrId = attrs.getAttributeResourceValue(i, 0);
            if (colorAttrId == 0) {
              String colorAttrValue = attrs.getAttributeValue(i);
              colorAttrId = Integer.valueOf(colorAttrValue.replace("?", ""));
              color = getColorFromAttribute(context, colorAttrId);
            } else {
              color = ContextCompat.getColor(context, colorAttrId);
            }
            break;
          case android.R.attr.alpha:
            try {
              alpha = attrs.getAttributeFloatValue(i, 1.0f);
            } catch (Exception e) {
              String alphaAttrValue = attrs.getAttributeValue(i);
              alpha = getFloatFromAttribute(context, Integer.valueOf(alphaAttrValue.replace("?", "")));
            }
            break;
          default:
            stateSpec[j++] = attrs.getAttributeBooleanValue(i, false) ? stateResId : -stateResId;
        }
      }
      stateSpec = StateSet.trimStateSet(stateSpec, j);
      color = modulateColorAlpha(color, alpha);

      customColorList.add(color);
      customStateList.add(stateSpec);
    }

    int[] colors = new int[customColorList.size()];
    int[][] states = new int[customStateList.size()][];
    int i = 0;
    for (int n = states.length; i < n; i++) {
      colors[i] = customColorList.get(i);
      states[i] = customStateList.get(i);
    }
    return new ColorStateList(states, colors);
  }

  public static float getFloatFromAttribute(Context context, int attrId) {
    if (attrId == 0) {
      return 0;
    }
    TypedValue a = new TypedValue();
    context.getTheme().resolveAttribute(attrId, a, true);
    return a.getFloat();
  }

  public static int getResourceIdFromAttribute(Context context, int attrId) {
    if (attrId == 0) {
      return 0;
    }
    TypedValue a = new TypedValue();
    context.getTheme().resolveAttribute(attrId, a, true);
    return a.resourceId;
  }

  public static int getStatusBarSize(Context context) {
    int statusBarSize = 0;
    if (context != null) {
      int id = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
      if (id > 0) {
        statusBarSize = context.getResources().getDimensionPixelSize(id);
      }
    }
    return statusBarSize;
  }

  public static boolean isLightTheme(Context context) {
    return getBooleanFromAttribute(context, R.attr.isLightTheme);
  }

  public static int modulateColorAlpha(int baseColor, float alphaMod) {
    if (alphaMod == 1.0f) {
      return baseColor;
    }
    int alpha = (int) (Color.alpha(baseColor) * alphaMod + 0.5f);
    alpha = Math.min(Math.max(alpha, 0), 255);
    return ColorUtils.setAlphaComponent(baseColor, alpha);
  }

  public static PorterDuff.Mode parseTintMode(int value, PorterDuff.Mode defaultMode) {
    switch (value) {
      default:
        return defaultMode;
      case 0:
        return PorterDuff.Mode.CLEAR;
      case 1:
        return PorterDuff.Mode.SRC;
      case 2:
        return PorterDuff.Mode.DST;
      case 3:
        return PorterDuff.Mode.SRC_OVER;
      case 4:
        return PorterDuff.Mode.DST_OVER;
      case 5:
        return PorterDuff.Mode.SRC_IN;
      case 6:
        return PorterDuff.Mode.DST_IN;
      case 7:
        return PorterDuff.Mode.SRC_OUT;
      case 8:
        return PorterDuff.Mode.DST_OUT;
      case 9:
        return PorterDuff.Mode.SRC_ATOP;
      case 10:
        return PorterDuff.Mode.DST_ATOP;
      case 11:
        return PorterDuff.Mode.XOR;
      case 16:
        return PorterDuff.Mode.DARKEN;
      case 17:
        return PorterDuff.Mode.LIGHTEN;
      case 13:
        return PorterDuff.Mode.MULTIPLY;
      case 14:
        return PorterDuff.Mode.SCREEN;
      case 12:
        return Build.VERSION.SDK_INT >= 11 ? PorterDuff.Mode.valueOf("ADD") : defaultMode;
      case 15:
        return Build.VERSION.SDK_INT >= 11 ? PorterDuff.Mode.valueOf("OVERLAY") : defaultMode;
    }
  }

  public static Drawable supportDrawableTint(Context context, Drawable drawable, Palette palette) {
    try {
      int resId = 0;
      switch (palette) {
        case PRIMARY:
          resId = MdCompat.getResourceIdFromAttribute(context, R.attr.mdIconColor_primaryPalette);
          break;
        case ACCENT:
          resId = MdCompat.getResourceIdFromAttribute(context, R.attr.mdIconColor_accentPalette);
          break;
        case WARN:
          resId = MdCompat.getResourceIdFromAttribute(context, R.attr.mdIconColor_warnPalette);
          break;
        case BACKGROUND:
          resId = MdCompat.getResourceIdFromAttribute(context, R.attr.mdIconColor_backgroundPalette);
          break;
      }
      return MdCompat.createDrawableTint(drawable, null, getColorStateList(context, resId), null);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (XmlPullParserException e) {
      e.printStackTrace();
    }
    return drawable;
  }

  public static void supportDrawableTint(Context context, Menu menu, Palette palette) {
    if (menu != null) {
      int i = 0;
      for (int n = menu.size(); i < n; i++) {
        supportDrawableTint(context, menu.getItem(i), palette);
      }
    }
  }

  public static void supportDrawableTint(Context context, MenuItem menuItem, Palette palette) {
    if (menuItem != null) {
      menuItem.setIcon(MdCompat.supportDrawableTint(context, menuItem.getIcon(), Palette.PRIMARY));
      if (menuItem.hasSubMenu()) {
        supportDrawableTint(context, menuItem.getSubMenu(), palette);
      }
    }
  }

  public static void supportDrawableTint(Context context, Toolbar toolbar, Palette palette) {
    if (toolbar != null) {
      toolbar.setNavigationIcon(supportDrawableTint(context, toolbar.getNavigationIcon(), palette));
    }
  }

  public static void supportDrawableTint(Context context, Toolbar toolbar) {
    supportDrawableTint(context, toolbar, Palette.PRIMARY);
  }

  public enum Palette {
    PRIMARY, ACCENT, WARN, BACKGROUND
  }
}
