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

  private boolean mIsLoading;

  private boolean mIsEmpty;

  private View vEmptyView;

  private View vLoadingView;

  private View vErrorView;

  private int mVisibility;

  private OnRecyclerViewChangeListener mOnRecyclerViewChangeListener;

  final private AdapterDataObserver mObserver = new AdapterDataObserver() {
    @Override
    public void onChanged() {
      updateEmptyView();
      updateRecyclerView();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
      updateEmptyView();
      updateRecyclerView();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
      updateEmptyView();
      updateRecyclerView();
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
    updateRecyclerView();
  }

  @Override
  public void setVisibility(int visibility) {
    super.setVisibility(visibility);
    mVisibility = visibility;
    updateErrorView();
    updateEmptyView();
    updateLoadingView();
    updateRecyclerView();
  }

  public void hideLoadingView() {
    mIsLoading = true;
    updateEmptyView();
    updateLoadingView();
    updateRecyclerView();
  }

  public RecycleEmptyErrorView hideErrorView() {
    mIsError = false;
    updateEmptyView();
    updateLoadingView();
    updateErrorView();
    updateRecyclerView();
    return this;
  }

  public RecycleEmptyErrorView hideEmptyView() {
    mIsEmpty = false;
    updateEmptyView();
    updateLoadingView();
    updateErrorView();
    updateRecyclerView();
    return this;
  }

  public RecycleEmptyErrorView setEmptyView(View emptyView) {
    if (vEmptyView != null) {
      vEmptyView.setVisibility(GONE);
    }
    vEmptyView = emptyView;
    vEmptyView.setVisibility(GONE);
    updateEmptyView();
    updateRecyclerView();
    return this;
  }

  public RecycleEmptyErrorView setLoadingView(View loadingView) {
    if (vLoadingView != null) {
      vLoadingView.setVisibility(GONE);
    }
    vLoadingView = loadingView;
    vLoadingView.setVisibility(GONE);
    updateEmptyView();
    updateLoadingView();
    updateRecyclerView();
    return this;
  }

  public RecycleEmptyErrorView setErrorView(View errorView) {
    if (vErrorView != null) {
      vErrorView.setVisibility(GONE);
    }
    vErrorView = errorView;
    vErrorView.setVisibility(GONE);
    updateEmptyView();
    updateLoadingView();
    updateErrorView();
    updateRecyclerView();
    return this;
  }

  public RecycleEmptyErrorView showEmptyView() {
    mIsEmpty = true;
    updateEmptyView();
    updateLoadingView();
    updateErrorView();
    updateRecyclerView();
    return this;
  }

  public RecycleEmptyErrorView showErrorView() {
    mIsError = true;
    updateEmptyView();
    updateLoadingView();
    updateErrorView();
    updateRecyclerView();
    return this;
  }

  private boolean shouldShowEmptyView() {
    if (vEmptyView != null &&
        ((getAdapter() != null && getAdapter().getItemCount() == 0 && !shouldShowLoadingView() && !shouldShowErrorView()) || mIsEmpty)) {
      return true;
    }
    return false;
  }

  private boolean shouldShowLoadingView() {
    if (vLoadingView != null && !mIsLoading && !shouldShowErrorView()) {
      return true;
    }
    return false;
  }

  private boolean shouldShowErrorView() {
    if (vErrorView != null && mIsError) {
      return true;
    }
    return false;
  }

  private void updateEmptyView() {
    if (vEmptyView != null) {
      vEmptyView.setVisibility(shouldShowEmptyView() && mVisibility == VISIBLE ? VISIBLE : GONE);
    }
  }

  private void updateLoadingView() {
    if (vLoadingView != null) {
      vLoadingView.setVisibility(shouldShowLoadingView() && mVisibility == VISIBLE ? VISIBLE : GONE);
    }
  }

  private void updateErrorView() {
    if (vErrorView != null) {
      vErrorView.setVisibility(shouldShowErrorView() && mVisibility == VISIBLE ? VISIBLE : GONE);
    }
  }

  private void updateRecyclerView() {
    super.setVisibility(
        !shouldShowEmptyView() && !shouldShowLoadingView() && !shouldShowErrorView() && mVisibility == VISIBLE ? VISIBLE : GONE);
    if (mOnRecyclerViewChangeListener != null) {
      if (isShowingErrorView()) {
        mOnRecyclerViewChangeListener.onRecyclerViewChange(ViewType.ERROR);
      } else if (isShowingLoadingView()) {
        mOnRecyclerViewChangeListener.onRecyclerViewChange(ViewType.LOADING);
      } else if (isShowingEmptyView()) {
        mOnRecyclerViewChangeListener.onRecyclerViewChange(ViewType.EMPTY);
      } else {
        mOnRecyclerViewChangeListener.onRecyclerViewChange(ViewType.RECYCLER);
      }
    }
  }

  public View getLoadingView() {
    return vLoadingView;
  }

  public View getEmptyView() {
    return vEmptyView;
  }

  public View getErrorView() {
    return vErrorView;
  }

  public boolean isShowingLoadingView() {
    if (vLoadingView != null && vLoadingView.getVisibility() == VISIBLE) {
      return true;
    }
    return false;
  }

  public boolean isShowingEmptyView() {
    if (vEmptyView != null && vEmptyView.getVisibility() == VISIBLE) {
      return true;
    }
    return false;
  }

  public boolean isShowingErrorView() {
    if (vErrorView != null && vEmptyView.getVisibility() == VISIBLE) {
      return true;
    }
    return false;
  }

  public boolean isShowingRecyclerView() {
    return getVisibility() == VISIBLE;
  }

  public void setOnRecyclerViewChangeListener(OnRecyclerViewChangeListener onRecyclerViewChangeListener) {
    mOnRecyclerViewChangeListener = onRecyclerViewChangeListener;
  }

  public enum ViewType {
    LOADING, EMPTY, ERROR, RECYCLER
  }

  public interface OnRecyclerViewChangeListener {

    void onRecyclerViewChange(ViewType type);
  }
}
