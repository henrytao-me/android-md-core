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

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import me.henrytao.mdcore.R;

/**
 * Created by henrytao on 10/17/15.
 */
public class MdListItem extends RelativeLayout {

  protected static int getDefaultStyleAttr(Context context, AttributeSet attrs, int styleAttr) {
    if (styleAttr > 0) {
      return styleAttr;
    }
    TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MdListItem, 0, 0);
    int defStyleAttr = R.attr.MdListItemSingleLineTextOnly;
    try {
      int type = a.getInteger(R.styleable.MdListItem_mdli_type, 11);
      switch (type) {
        case 11:
          defStyleAttr = R.attr.MdListItemSingleLineTextOnly;
          break;
        case 12:
          defStyleAttr = R.attr.MdListItemSingleLineIconWithText;
          break;
        case 13:
          defStyleAttr = R.attr.MdListItemSingleLineAvatarWithText;
          break;
        case 14:
          defStyleAttr = R.attr.MdListItemSingleLineAvatarWithTextAndIcon;
          break;
        case 21:
          defStyleAttr = R.attr.MdListItemTwoLineTextOnly;
          break;
        case 22:
          defStyleAttr = R.attr.MdListItemTwoLineIconWithText;
          break;
        case 23:
          defStyleAttr = R.attr.MdListItemTwoLineAvatarWithText;
          break;
        case 24:
          defStyleAttr = R.attr.MdListItemTwoLineAvatarWithTextAndIcon;
          break;
        case 31:
          defStyleAttr = R.attr.MdListItemThreeLineTextOnly;
          break;
        case 32:
          defStyleAttr = R.attr.MdListItemThreeLineIconWithText;
          break;
        case 33:
          defStyleAttr = R.attr.MdListItemThreeLineAvatarWithText;
          break;
        case 34:
          defStyleAttr = R.attr.MdListItemThreeLineAvatarWithTextAndIcon;
          break;
      }
    } finally {
      a.recycle();
    }
    return defStyleAttr;
  }

  public MdListItem(Context context) {
    this(context, null);
  }

  public MdListItem(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MdListItem(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, getDefaultStyleAttr(context, attrs, defStyleAttr));
    initFromAttributes(attrs);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public MdListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, getDefaultStyleAttr(context, attrs, defStyleAttr), defStyleRes);
    initFromAttributes(attrs);
  }

  protected void initFromAttributes(AttributeSet attrs) {
    renderInEditMode(attrs);
    //TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.SmoothCollapsingToolbarLayout, 0, 0);
    //try {
    //  mCollapsedOffsetX = a.getDimension(R.styleable.SmoothCollapsingToolbarLayout_sctl_collapsed_offsetX, 0);
    //  mCollapsedOffsetY = a.getDimension(R.styleable.SmoothCollapsingToolbarLayout_sctl_collapsed_offsetY, 0);
    //  mCollapsedAvatarSize = a.getDimension(R.styleable.SmoothCollapsingToolbarLayout_sctl_collapsed_avatarSize, -1);
    //  mCollapsedTitleTextSize = a.getDimension(R.styleable.SmoothCollapsingToolbarLayout_sctl_collapsed_titleTextSize, -1);
    //  mCollapsedSubTitleTextSize = a.getDimension(R.styleable.SmoothCollapsingToolbarLayout_sctl_collapsed_subtitleTextSize, -1);
    //
    //  mExpandedOffsetX = a.getDimension(R.styleable.SmoothCollapsingToolbarLayout_sctl_expanded_offsetX, 0);
    //  mExpandedOffsetY = a.getDimension(R.styleable.SmoothCollapsingToolbarLayout_sctl_expanded_offsetY, 0);
    //  mExpandedAvatarSize = a.getDimension(R.styleable.SmoothCollapsingToolbarLayout_sctl_expanded_avatarSize, -1);
    //  mExpandedTitleTextSize = a.getDimension(R.styleable.SmoothCollapsingToolbarLayout_sctl_expanded_titleTextSize, -1);
    //  mExpandedSubtitleTextSize = a.getDimension(R.styleable.SmoothCollapsingToolbarLayout_sctl_expanded_subtitleTextSize, -1);
    //
    //  mAvatarId = a.getResourceId(R.styleable.SmoothCollapsingToolbarLayout_sctl_avatar_id, 0);
    //  mTitleId = a.getResourceId(R.styleable.SmoothCollapsingToolbarLayout_sctl_title_id, 0);
    //  mSubtitleId = a.getResourceId(R.styleable.SmoothCollapsingToolbarLayout_sctl_subtitle_id, 0);
    //} finally {
    //  a.recycle();
    //}
  }

  protected void renderInEditMode(AttributeSet attrs) {
    if (isInEditMode()) {
      int[] attributes = new int[]{android.R.attr.minHeight};
      TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, attributes, 0, 0);
      try {
        setMinimumHeight(a.getDimensionPixelSize(0, 0));
      } finally {
        a.recycle();
      }
    }
  }
}
