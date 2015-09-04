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
import android.util.AttributeSet;

/**
 * Created by henrytao on 5/10/15.
 */
public class MdSwitchCompat extends android.support.v7.widget.SwitchCompat {

  private OnCheckedChangeListener mOnCheckedChangeListener;

  public MdSwitchCompat(Context context) {
    super(context);
  }

  public MdSwitchCompat(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public MdSwitchCompat(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void setChecked(boolean checked) {
    setChecked(checked, true);
  }

  @Override
  public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
    super.setOnCheckedChangeListener(listener);
    mOnCheckedChangeListener = listener;
  }

  public void setChecked(boolean checked, boolean isNotifyCheckedChangeListener) {
    if (isNotifyCheckedChangeListener) {
      super.setChecked(checked);
    } else {
      super.setOnCheckedChangeListener(null);
      super.setChecked(checked);
      super.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }
  }
}
