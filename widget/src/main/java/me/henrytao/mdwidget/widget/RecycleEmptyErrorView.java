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

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by henrytao on 5/1/15.
 */
public class RecycleEmptyErrorView extends ObservableRecyclerView {

  private boolean mIsError;

  private View vEmptyView;

  private View vErrorView;

  private int mVisibility;

  final private AdapterDataObserver mObserver = new AdapterDataObserver() {
    @Override
    public void onChanged() {
      updateEmptyView();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
      updateEmptyView();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
      updateEmptyView();
    }
  };

  public RecycleEmptyErrorView(Context context) {
    super(context);
    mVisibility = getVisibility();
  }

  public RecycleEmptyErrorView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mVisibility = getVisibility();
  }

  public RecycleEmptyErrorView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    mVisibility = getVisibility();
  }

  @Override
  public void setAdapter(Adapter adapter) {
    Adapter oldAdapter = getAdapter();
    if (oldAdapter != null) {
      oldAdapter.unregisterAdapterDataObserver(mObserver);
    }
    super.setAdapter(adapter);
    if (adapter != null) {
      adapter.registerAdapterDataObserver(mObserver);
    }
    updateEmptyView();
  }

  @Override
  public void setVisibility(int visibility) {
    super.setVisibility(visibility);
    mVisibility = visibility;
    updateErrorView();
    updateEmptyView();
  }

  public RecycleEmptyErrorView hideErrorView() {
    mIsError = false;
    updateErrorView();
    updateEmptyView();
    return this;
  }

  public RecycleEmptyErrorView setEmptyView(View emptyView) {
    if (vEmptyView != null) {
      vEmptyView.setVisibility(GONE);
    }
    vEmptyView = emptyView;
    vEmptyView.setVisibility(GONE);
    updateEmptyView();
    return this;
  }

  public RecycleEmptyErrorView setErrorView(View errorView) {
    if (vErrorView != null) {
      vErrorView.setVisibility(GONE);
    }
    vErrorView = errorView;
    vErrorView.setVisibility(GONE);
    updateErrorView();
    updateEmptyView();
    return this;
  }

  public RecycleEmptyErrorView showErrorView() {
    mIsError = true;
    updateErrorView();
    updateEmptyView();
    return this;
  }

  private boolean shouldShowErrorView() {
    if (vErrorView != null && mIsError) {
      return true;
    }
    return false;
  }

  private void updateEmptyView() {
    if (vEmptyView != null && getAdapter() != null) {
      boolean isShowEmptyView = getAdapter().getItemCount() == 0;
      vEmptyView.setVisibility(isShowEmptyView && !shouldShowErrorView() && mVisibility == VISIBLE ? VISIBLE : GONE);
      super.setVisibility(!isShowEmptyView && !shouldShowErrorView() && mVisibility == VISIBLE ? VISIBLE : GONE);
    }
  }

  private void updateErrorView() {
    if (vErrorView != null) {
      vErrorView.setVisibility(shouldShowErrorView() && mVisibility == VISIBLE ? VISIBLE : GONE);
    }
  }
}
