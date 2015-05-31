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

package me.henrytao.mdcore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import me.henrytao.mdcore.R;
import me.henrytao.mdcore.config.Constants;

/**
 * Created by henrytao on 5/20/15.
 */
public abstract class RecyclerViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

  public abstract int getDataItemCount();

  public abstract boolean hasFooter();

  public abstract boolean hasHeader();

  public abstract void onBindViewHolder(T holder, int position, int dataPosition);

  public abstract T onCreateViewHolder(ViewGroup parent, int viewType, Constants.ItemViewType itemViewType);

  protected Integer[] mSectionIndexes;

  protected Map<Integer, Object> mSections;

  @Override
  public int getItemCount() {
    return getDataItemCount() + (hasHeader() ? 1 : 0) + (hasFooter() ? 1 : 0) + getSectionIndexes().length;
  }

  @Override
  public int getItemViewType(int position) {
    if (isHeader(position)) {
      return Constants.RECYCLER_VIEW_ITEM_TYPE.HEADER;
    } else if (isFooter(position)) {
      return Constants.RECYCLER_VIEW_ITEM_TYPE.FOOTER;
    } else if (isSection(position)) {
      return Constants.RECYCLER_VIEW_ITEM_TYPE.SECTION;
    } else if (isBlank(position)) {
      return Constants.RECYCLER_VIEW_ITEM_TYPE.BLANK;
    }
    return Constants.RECYCLER_VIEW_ITEM_TYPE.ITEM;
  }

  @Override
  public void onBindViewHolder(T holder, int position) {
    onBindViewHolder(holder, position, positionToDataPosition(position));
  }

  @Override
  public T onCreateViewHolder(ViewGroup parent, int viewType) {
    Constants.ItemViewType itemViewType = Constants.ItemViewType.BLANK;
    if (viewType == Constants.RECYCLER_VIEW_ITEM_TYPE.HEADER) {
      itemViewType = Constants.ItemViewType.HEADER;
    } else if (viewType == Constants.RECYCLER_VIEW_ITEM_TYPE.SECTION) {
      itemViewType = Constants.ItemViewType.SECTION;
    } else if (viewType == Constants.RECYCLER_VIEW_ITEM_TYPE.ITEM) {
      itemViewType = Constants.ItemViewType.ITEM;
    }
    return onCreateViewHolder(parent, viewType, itemViewType);
  }

  /*
   * `originalIndex` is the index in your actual data
   * Ex:
   * originalIndex == 0: originalIndex will be above first item / first item in the list without header
   * originalIndex == 8: originalIndex will be above item index 8 /
   *                     8th item in the list without header if it's the first section /
   *                     9th item in the list without header if it's the second section
   */
  public void addSection(int dataIndex, Object title) {
    getSections().put(dataIndex, title);
    calculateSectionIndexes();
  }

  public void clearAllSection() {
    getSections().clear();
    calculateSectionIndexes();
  }

  public int dataPositionToPosition(int dataPosition) {
    Integer[] indexes = getSectionIndexes();
    int offset = indexes.length;
    for (int i = indexes.length - 1; i >= 0; i--) {
      if (dataPosition >= i) {
        break;
      }
      offset -= 1;
    }
    return dataPosition + offset + (hasHeader() ? 1 : 0);
  }

  public Integer[] getSectionIndexes() {
    if (mSectionIndexes == null) {
      mSectionIndexes = new Integer[0];
    }
    return mSectionIndexes;
  }

  public Map<Integer, Object> getSections() {
    if (mSections == null) {
      mSections = new HashMap<>();
    }
    return mSections;
  }

  public int positionToDataPosition(int position) {
    if (isHeader(position) || isFooter(position) || isBlank(position)) {
      return -1;
    }
    position -= hasHeader() ? 1 : 0;
    for (int i : getSectionIndexes()) {
      if (position > i) {
        position -= 1;
      } else {
        break;
      }
    }
    return position;
  }

  public void removeSection(int dataIndex) {
    getSections().remove(dataIndex);
    calculateSectionIndexes();
  }

  protected int getSectionPosition(int dataPosition) {
    Integer[] indexes = getSectionIndexes();
    int offset = indexes.length;
    for (int i = indexes.length - 1; i >= 0; i--) {
      if (dataPosition >= indexes[i]) {
        dataPosition = indexes[i];
        break;
      }
      offset -= 1;
    }
    return dataPosition - 1 + offset + (hasHeader() ? 1 : 0);
  }

  protected boolean isBlank(int position) {
    int count = getDataItemCount();
    int offset = 0;
    for (int i : getSectionIndexes()) {
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

  protected boolean isSection(int position) {
    position -= hasHeader() ? 1 : 0;
    int offset = 0;
    for (int i : getSectionIndexes()) {
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

  private void calculateSectionIndexes() {
    mSectionIndexes = getSections().keySet().toArray(new Integer[getSections().size()]);
    Arrays.sort(mSectionIndexes);
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

  public static class SectionHolder extends RecyclerView.ViewHolder {

    public static SectionHolder newInstance(Context context, ViewGroup parent) {
      View view = LayoutInflater.from(context).inflate(R.layout.md_list_section, parent, false);
      return new SectionHolder(view);
    }

    View vContainer;

    TextView vTitle;

    public SectionHolder(View itemView) {
      super(itemView);
      vContainer = itemView;
      vTitle = (TextView) vContainer.findViewById(R.id.title);
    }

    public void bind(Object title) {
      vContainer.setVisibility(title == null ? View.GONE : View.VISIBLE);
      if (title != null) {
        if (title instanceof Integer) {
          // resouceId
          vTitle.setText((Integer) title);
        } else {
          vTitle.setText(title.toString());
        }
      }
    }
  }
}
