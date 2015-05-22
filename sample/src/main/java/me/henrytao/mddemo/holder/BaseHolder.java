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

package me.henrytao.mddemo.holder;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import me.henrytao.mddemo.util.ResourceUtils;


/**
 * Created by henrytao on 5/10/15.
 */
public abstract class BaseHolder<T extends BaseHolder> {

  protected abstract int getLayoutId();

  protected abstract void onCreate(Context context, View view);

  private boolean mAttachToRoot;

  private Context mContext;

  private ViewGroup mParent;

  private View mView;

  public BaseHolder(Context context) {
    this(context, null);
  }

  public BaseHolder(Context context, ViewGroup parent) {
    this(context, parent, false);
  }

  public BaseHolder(Context context, ViewGroup parent, Boolean attachToRoot) {
    mContext = context;
    mParent = parent;
    mAttachToRoot = attachToRoot;
    onCreate(context, getView());
  }

  public T destroy() {
    onDestroy();
    return (T) this;
  }

  public Context getContext() {
    return mContext;
  }

  public View getView() {
    if (mView == null) {
      mView = ResourceUtils.inflate(getContext(), getLayoutId(), mParent, mAttachToRoot);
    }
    return mView;
  }

  public T pause() {
    onPause();
    return (T) this;
  }

  public T resume() {
    onResume();
    return (T) this;
  }

  protected void onDestroy() {

  }

  protected void onPause() {

  }

  protected void onResume() {

  }

}
