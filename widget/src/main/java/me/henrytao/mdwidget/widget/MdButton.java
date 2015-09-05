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

package me.henrytao.mdwidget.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import me.henrytao.widget.R;

/**
 * Created by henrytao on 8/23/15.
 */
public class MdButton extends AppCompatButton {

  private static final float sDisableAlpha = 0.54f;

  private float mDisableAlpha;

  public MdButton(Context context) {
    super(context);
    init(context, null);
  }

  public MdButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public MdButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  @Override
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);
    setDisableAlpha(enabled);
  }

  protected void init(Context context, AttributeSet attrs) {
    mDisableAlpha = sDisableAlpha;
    if (attrs != null) {
      TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MdButton, 0, 0);
      mDisableAlpha = a.getFloat(R.styleable.MdButton_md_disable_alpha, sDisableAlpha);
      a.recycle();
    }
    setDisableAlpha(isEnabled());
  }

  protected void setDisableAlpha(boolean enabled) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      setAlpha(enabled ? 1 : mDisableAlpha);
    }
  }
}
