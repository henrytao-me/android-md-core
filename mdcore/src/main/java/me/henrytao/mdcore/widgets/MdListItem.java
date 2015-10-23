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
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import me.henrytao.mdcore.R;

/**
 * Created by henrytao on 10/17/15.
 */
public class MdListItem extends RelativeLayout {

  protected static final int DEFAULT_TYPE = 11;

  protected static Map<Integer, ListItemInfo> sListItemInfos = new HashMap<>();

  static {
    sListItemInfos.put(11, new ListItemInfo(R.attr.MdListItemSingleLineTextOnly, R.layout.mdli_single_line_text_only));
    sListItemInfos.put(12, new ListItemInfo(R.attr.MdListItemSingleLineIconWithText, R.layout.mdli_single_line_icon_with_text));
    sListItemInfos.put(13, new ListItemInfo(R.attr.MdListItemSingleLineAvatarWithText, R.layout.mdli_single_line_avatar_with_text));
    sListItemInfos.put(14, new ListItemInfo(R.attr.MdListItemSingleLineAvatarWithTextAndIcon,
        R.layout.mdli_single_line_avatar_with_text_and_icon));

    sListItemInfos.put(21, new ListItemInfo(R.attr.MdListItemTwoLineTextOnly, R.layout.mdli_two_line_text_only));
    sListItemInfos.put(22, new ListItemInfo(R.attr.MdListItemTwoLineIconWithText, R.layout.mdli_two_line_icon_with_text));
    sListItemInfos.put(23, new ListItemInfo(R.attr.MdListItemTwoLineAvatarWithText, R.layout.mdli_two_line_avatar_with_text));
    sListItemInfos.put(24, new ListItemInfo(R.attr.MdListItemTwoLineAvatarWithTextAndIcon,
        R.layout.mdli_two_line_avatar_with_text_and_icon));

    sListItemInfos.put(31, new ListItemInfo(R.attr.MdListItemThreeLineTextOnly, R.layout.mdli_three_line_text_only));
    sListItemInfos.put(32, new ListItemInfo(R.attr.MdListItemThreeLineIconWithText, R.layout.mdli_three_line_icon_with_text));
    sListItemInfos.put(33, new ListItemInfo(R.attr.MdListItemThreeLineAvatarWithText, R.layout.mdli_three_line_avatar_with_text));
    sListItemInfos.put(34, new ListItemInfo(R.attr.MdListItemThreeLineAvatarWithTextAndIcon,
        R.layout.mdli_three_line_avatar_with_text_and_icon));
  }

  protected static int getDefaultLayout(int type) {
    if (!sListItemInfos.containsKey(type)) {
      return sListItemInfos.get(DEFAULT_TYPE).mDefLayout;
    }
    return sListItemInfos.get(type).mDefLayout;
  }

  protected static int getDefaultStyleAttr(Context context, AttributeSet attrs, int styleAttr) {
    if (styleAttr > 0) {
      return styleAttr;
    }
    TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MdListItem, 0, 0);
    int defStyleAttr = sListItemInfos.get(DEFAULT_TYPE).mDefStyleAttr;
    try {
      int type = a.getInteger(R.styleable.MdListItem_mdli_type, DEFAULT_TYPE);
      if (sListItemInfos.containsKey(type)) {
        defStyleAttr = sListItemInfos.get(type).mDefStyleAttr;
      }
    } finally {
      a.recycle();
    }
    return defStyleAttr;
  }

  protected Drawable mAvatar;

  protected int mDividerLayout;

  protected Drawable mIcon;

  protected String mSubtitle;

  protected String mTitle;

  protected int mType;

  protected ImageView vAvatar;

  protected ImageView vIcon;

  protected TextView vSubtitle;

  protected TextView vTitle;

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

  @Override
  protected void onFinishInflate() {
    int count = getChildCount();
    if (count == 0) {
      LayoutInflater.from(getContext()).inflate(getDefaultLayout(mType), this, true);
    }
    if (mDividerLayout > 0 && !isInEditMode()) {
      LayoutInflater.from(getContext()).inflate(mDividerLayout, this, true);
    }
    super.onFinishInflate();
    vTitle = (TextView) findViewById(android.R.id.text1);
    vSubtitle = (TextView) findViewById(android.R.id.text2);
    vAvatar = (ImageView) findViewById(android.R.id.icon1);
    vIcon = (ImageView) findViewById(android.R.id.icon2);
    setTitle(mTitle);
    setSubtitle(mSubtitle);
    setAvatar(mAvatar);
    setIcon(mIcon);
  }

  public void setAvatar(Drawable drawable) {
    if (vAvatar != null) {
      vAvatar.setImageDrawable(drawable);
    }
  }

  public void setAvatar(@DrawableRes int resId) {
    if (vAvatar != null) {
      vAvatar.setImageResource(resId);
    }
  }

  public void setIcon(Drawable drawable) {
    if (vIcon != null) {
      vIcon.setImageDrawable(drawable);
    }
  }

  public void setIcon(@DrawableRes int resId) {
    if (vIcon != null) {
      vIcon.setImageResource(resId);
    }
  }

  public void setSubtitle(@StringRes int resId) {
    if (vSubtitle != null) {
      vSubtitle.setText(resId);
    }
  }

  public void setSubtitle(CharSequence subtitle) {
    if (vSubtitle != null) {
      vSubtitle.setText(subtitle);
    }
  }

  public void setTitle(@StringRes int resId) {
    if (vTitle != null) {
      vTitle.setText(resId);
    }
  }

  public void setTitle(CharSequence title) {
    if (vTitle != null) {
      vTitle.setText(title);
    }
  }

  protected void initFromAttributes(AttributeSet attrs) {
    renderInEditMode(attrs);
    TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.MdListItem, 0, 0);
    try {
      mType = a.getInteger(R.styleable.MdListItem_mdli_type, DEFAULT_TYPE);
      mDividerLayout = a.getResourceId(R.styleable.MdListItem_mdli_divider_layout, 0);
      mTitle = a.getString(R.styleable.MdListItem_mdli_title);
      mSubtitle = a.getString(R.styleable.MdListItem_mdli_subtitle);
      mAvatar = a.getDrawable(R.styleable.MdListItem_mdli_avatar);
      mIcon = a.getDrawable(R.styleable.MdListItem_mdli_icon);
    } finally {
      a.recycle();
    }
  }

  protected void renderInEditMode(AttributeSet attrs) {
    if (isInEditMode()) {
      // TODO: render some special ui in preview mode
    }
  }

  protected static class ListItemInfo {

    public int mDefLayout;

    public int mDefStyleAttr;

    public ListItemInfo(int defStyleAttr, int defLayout) {
      mDefStyleAttr = defStyleAttr;
      mDefLayout = defLayout;
    }
  }
}
