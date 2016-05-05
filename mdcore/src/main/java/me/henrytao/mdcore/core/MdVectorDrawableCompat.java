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

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.AttributeSet;
import android.util.Xml;

/**
 * Created by henrytao on 5/5/16.
 */
public class MdVectorDrawableCompat {

  @Nullable
  public static VectorDrawableCompat create(@NonNull Context context, @DrawableRes int resId) {
    Resources res = context.getResources();
    Resources.Theme theme = context.getTheme();
    VectorDrawableCompat drawable = VectorDrawableCompat.create(res, resId, theme);

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      try {
        XmlPullParser parser = res.getXml(resId);
        AttributeSet attrs = Xml.asAttributeSet(parser);
        int type;
        while ((type = parser.next()) != XmlPullParser.START_TAG && type != XmlPullParser.END_DOCUMENT) {
          // Empty loop
        }
        if (type != XmlPullParser.START_TAG) {
          throw new XmlPullParserException("No start tag found");
        }

        TypedArray a = theme.obtainStyledAttributes(attrs, new int[]{
            android.R.attr.tint
        }, 0, 0);
        int tintId = a.getResourceId(0, 0);
        drawable.setTintList(MdCompat.getColorStateList(context, tintId));
      } catch (Exception ignore) {
        ignore.printStackTrace();
      }
    }

    return drawable;
  }
}
