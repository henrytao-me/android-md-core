/*
 * Copyright (C) 2015 MySQUAR. All rights reserved.
 *
 * This software is the confidential and proprietary information of MySQUAR or one of its
 * subsidiaries. You shall not disclose this confidential information and shall use it only in
 * accordance with the terms of the license agreement or other applicable agreement you entered into
 * with MySQUAR.
 *
 * MySQUAR MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. MySQUAR SHALL NOT BE LIABLE FOR ANY LOSSES
 * OR DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR
 * ITS DERIVATIVES.
 */

package me.henrytao.mdwidget.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by henrytao on 5/10/15.
 */
public class CheckableSwitchCompat extends android.support.v7.widget.SwitchCompat {

  private OnCheckedChangeListener mOnCheckedChangeListener;

  public CheckableSwitchCompat(Context context) {
    super(context);
  }

  public CheckableSwitchCompat(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CheckableSwitchCompat(Context context, AttributeSet attrs, int defStyleAttr) {
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
