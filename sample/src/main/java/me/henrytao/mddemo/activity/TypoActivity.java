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
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.henrytao.mddemo.R;
import me.henrytao.mddemo.fragment.ExampleMdTextFragment;

public class TypoActivity extends AppCompatActivity {

  public static Intent newIntent(Context context) {
    return new Intent(context, TypoActivity.class);
  }

  @Bind(R.id.spinner)
  Spinner vSpinner;

  @Bind(R.id.toolbar)
  Toolbar vToolbar;

  private ExampleMdTextFragment mFragment;

  private SpinnerAdapter mSpinnerAdapter;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_typo, menu);
    return true;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_typo);
    ButterKnife.bind(this);

    mFragment = ExampleMdTextFragment.newInstance();
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.fragment, mFragment)
        .commit();

    setSupportActionBar(vToolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    vToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });

    mSpinnerAdapter = new SpinnerAdapter(this);
    mSpinnerAdapter.add("English-like scripts");
    mSpinnerAdapter.add("Dense scripts");
    mSpinnerAdapter.add("Tall scripts");

    vSpinner.setAdapter(mSpinnerAdapter);
    vSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        onSpinnerItemSelected(view, position);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
  }

  protected void onSpinnerItemSelected(View view, int position) {
    Configuration config = new Configuration(getResources().getConfiguration());
    switch (position) {
      case 0:
        config.locale = Locale.US;
        break;
      case 1:
        config.locale = Locale.CHINESE;
        break;
      case 2:
        config.locale = new Locale("th", "TH");
        break;
    }
    getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    mFragment = ExampleMdTextFragment.newInstance();
    getSupportFragmentManager()
        .beginTransaction()
        .remove(mFragment)
        .replace(R.id.fragment, mFragment)
        .commit();
  }

  public static class SpinnerAdapter extends ArrayAdapter<String> {

    public SpinnerAdapter(Context context) {
      super(context, R.layout.item_spinner, R.id.title);
    }
  }
}
