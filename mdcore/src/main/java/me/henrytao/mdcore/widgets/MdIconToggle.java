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

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;

import java.io.IOException;

import me.henrytao.mdcore.R;
import me.henrytao.mdcore.utils.ResourceUtils;

/**
 * Created by henrytao on 10/26/15.
 */
public class MdIconToggle extends AppCompatCheckBox {

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
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      int numAttrs = attrs.getAttributeCount();
      for (int i = 0; i < numAttrs; i++) {
        if (attrs.getAttributeNameResource(i) == R.attr.buttonTint) {
          int buttonTintId = attrs.getAttributeResourceValue(i, 0);
          if (buttonTintId > 0) {
            try {
              setSupportButtonTintList(ResourceUtils.createColorStateListFromResId(context, buttonTintId));
            } catch (XmlPullParserException e) {
              e.printStackTrace();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }
      }
    }
  }
}
