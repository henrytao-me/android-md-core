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

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import me.henrytao.widget.R;

/**
 * Created by henrytao on 7/27/15.
 */
public class ViewWrapper extends View {

  private int mTargetId = 0;

  public ViewWrapper(Context context) {
    super(context);
    init(context, null);
  }

  public ViewWrapper(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public ViewWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public ViewWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context, attrs);
  }

  protected void init(Context context, AttributeSet attrs) {
    if (isInEditMode()) {
      return;
    }
    if (attrs != null) {
      TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ViewWrapper, 0, 0);
      mTargetId = a.getResourceId(R.styleable.ViewWrapper_target_id, 0);
      a.recycle();
    }
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    sync();
  }

  public ViewWrapper setTargetId(int targetId) {
    mTargetId = targetId;
    return this;
  }

  public void sync() {
    if (mTargetId > 0) {
      View targetView = getRootView().findViewById(mTargetId);
      if (targetView != null) {
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = targetView.getLayoutParams().height;
        setLayoutParams(params);
      }
    }
  }
}
