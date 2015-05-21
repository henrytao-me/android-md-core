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

package me.henrytao.mdwidget.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import me.henrytao.mdwidget.config.Constants;

/**
 * Created by henrytao on 5/20/15.
 */
public abstract class RecycleViewHSFAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

  public abstract void onBindViewHolder(T holder, int position, int dataPosition);

  protected abstract int getDataItemCount();

  protected abstract boolean hasFooter();

  protected abstract boolean hasHeader();

  protected Integer[] mSubheaderIndexes;

  protected Map<Integer, Object> mSubheaders;

  @Override
  public int getItemCount() {
    return getDataItemCount() + (hasHeader() ? 1 : 0) + (hasFooter() ? 1 : 0) + getSubheaderIndexes().length;
  }

  @Override
  public int getItemViewType(int position) {
    if (isHeader(position)) {
      return Constants.RECYCLE_ITEM_VIEW_TYPE.HEADER;
    } else if (isFooter(position)) {
      return Constants.RECYCLE_ITEM_VIEW_TYPE.FOOTER;
    } else if (isSubheader(position)) {
      return Constants.RECYCLE_ITEM_VIEW_TYPE.SUBHEADER;
    } else if (isBlank(position)) {
      return Constants.RECYCLE_ITEM_VIEW_TYPE.BLANK;
    }
    return Constants.RECYCLE_ITEM_VIEW_TYPE.ITEM;
  }

  @Override
  public void onBindViewHolder(T holder, int position) {
    onBindViewHolder(holder, position, positionToDataPosition(position));
  }

  /*
   * `originalIndex` is the index in your actual data
   * Ex:
   * originalIndex == 0: originalIndex will be above first item / first item in the list without header
   * originalIndex == 8: originalIndex will be above item index 8 /
   *                     8th item in the list without header if it's the first subheader /
   *                     9th item in the list without header if it's the second subheader
   */
  public void addSubheader(int dataIndex, Object title) {
    getSubheaders().put(dataIndex, title);
    calculateSubheaderIndexes();
  }

  public void clearSubheaders() {
    getSubheaders().clear();
    calculateSubheaderIndexes();
  }

  public Integer[] getSubheaderIndexes() {
    if (mSubheaderIndexes == null) {
      mSubheaderIndexes = new Integer[0];
    }
    return mSubheaderIndexes;
  }

  public Map<Integer, Object> getSubheaders() {
    if (mSubheaders == null) {
      mSubheaders = new HashMap<>();
    }
    return mSubheaders;
  }

  public void removeSubheader(int dataIndex) {
    getSubheaders().remove(dataIndex);
    calculateSubheaderIndexes();
  }

  protected boolean isBlank(int position) {
    int count = getDataItemCount();
    int offset = 0;
    for (int i : getSubheaderIndexes()) {
      if (i < count) {
        offset += 1;
      } else {
        break;
      }
    }
    int maxCount = count + (hasHeader() ? 1 : 0) + (hasFooter() ? 1 : 0) + offset;
    return !(position < maxCount);
  }

  protected boolean isFooter(int position) {
    return hasFooter() && position == (getItemCount() - 1);
  }

  protected boolean isHeader(int position) {
    return hasHeader() && position == 0;
  }

  protected boolean isSubheader(int position) {
    position -= hasHeader() ? 1 : 0;
    int offset = 0;
    for (int i : getSubheaderIndexes()) {
      if (position < (i + offset)) {
        break;
      }
      if (position == (i + offset)) {
        return true;
      }
      offset += 1;
    }
    return false;
  }

  protected int positionToDataPosition(int position) {
    if (isHeader(position) || isFooter(position) || isBlank(position)) {
      return -1;
    }
    position -= hasHeader() ? 1 : 0;
    for (int i : getSubheaderIndexes()) {
      if (position > i) {
        position -= 1;
      } else {
        break;
      }
    }
    return position;
  }

  private void calculateSubheaderIndexes() {
    mSubheaderIndexes = getSubheaders().keySet().toArray(new Integer[getSubheaders().size()]);
    Arrays.sort(mSubheaderIndexes);
  }

  public static class BlankHolder extends RecyclerView.ViewHolder {

    public static BlankHolder newInstance(Context context) {
      View view = new View(context);
      return new BlankHolder(view);
    }

    public BlankHolder(View itemView) {
      super(itemView);
    }
  }

}
