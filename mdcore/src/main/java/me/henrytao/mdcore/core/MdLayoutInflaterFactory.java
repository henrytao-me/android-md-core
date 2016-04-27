/*
 * Copyright 2016 "Henry Tao <hi@henrytao.me>"
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

package me.henrytao.mdcore.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.View;

import me.henrytao.mdcore.utils.Ln;
import me.henrytao.mdcore.widgets.MdButton;

/**
 * Created by henrytao on 4/27/16.
 */
public class MdLayoutInflaterFactory implements LayoutInflaterFactory {

  private static final String SUPPORT_BUTTON = "Button";

  private final AppCompatDelegate mDelegate;

  public MdLayoutInflaterFactory(@NonNull AppCompatDelegate delegate) {
    mDelegate = delegate;
  }

  @Override
  public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
    Ln.d("custom | %s | %s", name, attrs.getClass().toString());
    switch (name) {
      case SUPPORT_BUTTON:
        return new MdButton(context, attrs);
    }
    return mDelegate.createView(parent, name, context, attrs);
  }
}
