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

package me.henrytao.mdcore.widgets;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.Xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.henrytao.mdcore.R;
import me.henrytao.mdcore.utils.ResourceUtils;

/**
 * Created by henrytao on 10/26/15.
 */
public class MdIconToggle extends AppCompatCheckBox {

  public static ColorStateList parser(Context context, XmlPullParser parser) throws IOException, XmlPullParserException {
    AttributeSet attrs = Xml.asAttributeSet(parser);
    Resources r = context.getResources();

    final int innerDepth = parser.getDepth() + 1;
    int depth;
    int type;

    List<int[]> customStateList = new ArrayList<>();
    List<Integer> customColorList = new ArrayList<>();

    while ((type = parser.next()) != XmlPullParser.END_DOCUMENT
        && ((depth = parser.getDepth()) >= innerDepth || type != XmlPullParser.END_TAG)) {
      if (type != XmlPullParser.START_TAG || !parser.getName().equals("item")) {
        continue;
      }

      //TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.color});
      //int color = a.getColor(0, 0);
      //a.recycle();

      // Parse all unrecognized attributes as state specifiers.
      int j = 0;
      final int numAttrs = attrs.getAttributeCount();
      int[] stateSpec = new int[numAttrs];
      int color = 0;
      for (int i = 0; i < numAttrs; i++) {
        final int stateResId = attrs.getAttributeNameResource(i);
        switch (stateResId) {
          case android.R.attr.color:
            color = r.getColor(attrs.getAttributeResourceValue(i, 0));
            break;
          case android.R.attr.alpha:
            // Recognized attribute, ignore.
            break;
          default:
            stateSpec[j++] = attrs.getAttributeBooleanValue(i, false) ? stateResId : -stateResId;
        }
      }
      stateSpec = StateSet.trimStateSet(stateSpec, j);

      customColorList.add(color);
      customStateList.add(stateSpec);
    }

    int[][] states = new int[customStateList.size()][];
    int[] colors = new int[customColorList.size()];
    int i = 0;
    for (int n = states.length; i < n; i++) {
      states[i] = customStateList.get(i);
      colors[i] = customColorList.get(i);
    }
    return new ColorStateList(states, colors);
  }

  public MdIconToggle(Context context) {
    super(context);
    initFromAttributes(null);
  }

  public MdIconToggle(Context context, AttributeSet attrs) {
    super(context, attrs);
    initFromAttributes(attrs);
  }

  public MdIconToggle(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initFromAttributes(attrs);
  }

  protected void initFromAttributes(AttributeSet attrs) {
    Context context = getContext();
    ColorStateList colorStateList = new ColorStateList(
        new int[][]{
            new int[]{-android.R.attr.state_checked},
            new int[]{android.R.attr.state_checked}
        },
        new int[]{
            ResourceUtils.getColorFromAttribute(context, R.attr.mdTextColorSecondary_backgroundPalette),
            ResourceUtils.getColorFromAttribute(context, R.attr.mdColor_accentPalette)
        }
    );
    //setSupportButtonTintList(colorStateList);

    TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MdIconToggle, 0, 0);
    try {
      int resId = a.getResourceId(R.styleable.MdIconToggle_customTint, 0);
      if (resId > 0) {
        try {
          XmlResourceParser parser = context.getResources().getXml(resId);
          //ColorStateList cl = ColorStateList.createFromXml(context.getResources(), parser);
          //ColorStateList cl = parser(context, parser);

          ColorStateList cl = ResourceUtils.createColorStateListFromResId(context, resId);
          setSupportButtonTintList(cl);
        } catch (XmlPullParserException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      a.recycle();
    }

    //TypedValue typedValue = new TypedValue();
    //Resources.Theme theme = context.getTheme();
    //theme.resolveAttribute(R.attr.buttonTint, typedValue, true);
    //XmlResourceParser parser = context.getResources().getXml(typedValue.resourceId);
    //try {
    //  ColorStateList colorStateList = ColorStateList.createFromXml(context.getResources(), parser);
    //} catch (Exception e) {
    //}
  }
}
