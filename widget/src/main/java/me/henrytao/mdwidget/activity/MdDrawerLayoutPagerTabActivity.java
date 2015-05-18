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

package me.henrytao.mdwidget.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import me.henrytao.widget.R;

/**
 * Created by henrytao on 5/17/15.
 */
public abstract class MdDrawerLayoutPagerTabActivity extends MdPagerTabActivity {

  protected abstract int getDrawerContentResource();

  protected abstract int getDrawerLayoutResource();

  protected abstract int getDrawerNavigationResource();

  protected View vDrawerContent;

  protected DrawerLayout vDrawerLayout;

  protected View vDrawerNavigation;

  protected View vDrawerNavigationRight;

  protected ActionBarDrawerToggle vDrawerToggle;

  private WeakReference<MdDrawerLayoutPagerTabActivity> mWeakReference;

  @Override
  public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);
    initDrawer();
  }

  @Override
  public void setContentView(View view) {
    super.setContentView(view);
    initDrawer();
  }

  @Override
  public void setContentView(View view, ViewGroup.LayoutParams params) {
    super.setContentView(view, params);
    initDrawer();
  }

  public View getDrawerContent() {
    if (vDrawerContent == null) {
      vDrawerContent = findViewById(getDrawerContentResource());
    }
    return vDrawerContent;
  }

  public DrawerLayout getDrawerLayout() {
    if (vDrawerLayout == null) {
      vDrawerLayout = (DrawerLayout) findViewById(getDrawerLayoutResource());
    }
    return vDrawerLayout;
  }

  public View getDrawerNavigation() {
    if (vDrawerNavigation == null) {
      vDrawerNavigation = findViewById(getDrawerNavigationResource());
    }
    return vDrawerNavigation;
  }

  public View getDrawerNavigationRight() {
    if (vDrawerNavigationRight == null && getDrawerNavigationRightResource() > 0) {
      vDrawerNavigationRight = findViewById(getDrawerNavigationRightResource());
    }
    return vDrawerNavigationRight;
  }

  public void onDrawerClosed(NAVIGATION_DRAWER_TYPE type, View drawerView) {

  }

  public void onDrawerOpened(NAVIGATION_DRAWER_TYPE type, View drawerView) {

  }

  public void onDrawerSlide(NAVIGATION_DRAWER_TYPE type, View drawerView, float slideOffset) {

  }

  public void onDrawerStateChanged(int newState) {

  }

  public void openDrawer() {
    if (isValidated()) {
      getDrawerLayout().openDrawer(getDrawerNavigation());
    }
  }

  public void openDrawer(NAVIGATION_DRAWER_TYPE type) {
    if (type == NAVIGATION_DRAWER_TYPE.LEFT) {
      getDrawerLayout().openDrawer(getDrawerNavigation());
    } else if (type == NAVIGATION_DRAWER_TYPE.RIGHT && getDrawerNavigationRight() != null) {
      getDrawerLayout().openDrawer(getDrawerNavigationRight());
    }
  }

  protected int getDrawerCloseStringResource() {
    return R.string.close;
  }

  protected int getDrawerNavigationRightResource() {
    return 0;
  }

  protected int getDrawerOpenStringResource() {
    return R.string.open;
  }

  protected void initDrawer() {
    if (isValidated()) {
      getWeakReference();
      vDrawerToggle = new ActionBarDrawerToggle(this, getDrawerLayout(), getDrawerOpenStringResource(), getDrawerCloseStringResource()) {

        @Override
        public void onDrawerClosed(View drawerView) {
          super.onDrawerClosed(drawerView);
          MdDrawerLayoutPagerTabActivity activity = getWeakReference().get();
          if (activity != null) {
            NAVIGATION_DRAWER_TYPE type = NAVIGATION_DRAWER_TYPE.LEFT;
            if (activity.getDrawerNavigationRight() != null && drawerView.getId() == activity.getDrawerNavigationRight().getId()) {
              type = NAVIGATION_DRAWER_TYPE.RIGHT;
            }
            activity.onDrawerClosed(type, drawerView);
          }
        }

        @Override
        public void onDrawerOpened(View drawerView) {
          super.onDrawerOpened(drawerView);
          MdDrawerLayoutPagerTabActivity activity = getWeakReference().get();
          if (activity != null) {
            NAVIGATION_DRAWER_TYPE type = NAVIGATION_DRAWER_TYPE.LEFT;
            if (activity.getDrawerNavigationRight() != null && drawerView.getId() == activity.getDrawerNavigationRight().getId()) {
              type = NAVIGATION_DRAWER_TYPE.RIGHT;
            }
            activity.onDrawerOpened(type, drawerView);
          }
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
          super.onDrawerSlide(drawerView, slideOffset);
          MdDrawerLayoutPagerTabActivity activity = getWeakReference().get();
          if (activity != null) {
            NAVIGATION_DRAWER_TYPE type = NAVIGATION_DRAWER_TYPE.LEFT;
            if (activity.getDrawerNavigationRight() != null && drawerView.getId() == activity.getDrawerNavigationRight().getId()) {
              type = NAVIGATION_DRAWER_TYPE.RIGHT;
            }
            activity.onDrawerSlide(type, drawerView, slideOffset);
          }
        }

        @Override
        public void onDrawerStateChanged(int newState) {
          super.onDrawerStateChanged(newState);
          MdDrawerLayoutPagerTabActivity activity = getWeakReference().get();
          if (activity != null) {
            activity.onDrawerStateChanged(newState);
          }
        }
      };
      getDrawerLayout().setDrawerListener(vDrawerToggle);
    }
  }

  private WeakReference<MdDrawerLayoutPagerTabActivity> getWeakReference() {
    if (mWeakReference == null) {
      mWeakReference = new WeakReference<>(this);
    }
    return mWeakReference;
  }

  private boolean isValidated() {
    return getDrawerLayout() != null && getDrawerContent() != null && getDrawerNavigation() != null &&
        !(getDrawerNavigationRightResource() > 0 && getDrawerNavigationRight() == null);
  }

  public enum NAVIGATION_DRAWER_TYPE {
    LEFT,
    RIGHT
  }

}
