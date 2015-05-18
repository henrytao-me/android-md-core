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

package me.henrytao.mddemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import me.henrytao.mdcore.activity.MdToolbarActivity;
import me.henrytao.mddemo.R;

public class ExampleActivity extends MdToolbarActivity {

  private static String FRAGMENT_NAME = "fragment_name";

  private static String FRAGMENT_TITLE = "fragment_title";

  public static Intent getIntent(Context context, Class fragment, String title) {
    Intent intent = new Intent(context, ExampleActivity.class);
    intent.putExtra(FRAGMENT_NAME, fragment.getName());
    intent.putExtra(FRAGMENT_TITLE, title);
    return intent;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_example, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected int getToolbarResource() {
    return R.id.toolbar;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_example);
    setTitle(getIntent().getStringExtra(FRAGMENT_TITLE));
    try {
      Class objectClass = Class.forName(getIntent().getStringExtra(FRAGMENT_NAME));
      Constructor constructor = objectClass.getConstructor();
      Fragment fragment = (Fragment) constructor.newInstance();
      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.fragment, fragment)
          .commit();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
  }
}
