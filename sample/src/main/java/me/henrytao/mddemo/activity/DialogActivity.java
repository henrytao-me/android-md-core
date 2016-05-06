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

package me.henrytao.mddemo.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.CheckBox;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.henrytao.mddemo.R;
import me.henrytao.mddemo.utils.DialogUtils;

/**
 * Created by henrytao on 5/5/16.
 */
public class DialogActivity extends BaseActivity {

  @Bind(R.id.toolbar)
  Toolbar vToolbar;

  @Override
  protected int getDefaultLayout() {
    return R.layout.activity_dialog;
  }

  @Override
  protected int getMdCoreLayout() {
    return R.layout.activity_dialog;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);

    setSupportActionBar(vToolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    vToolbar.setNavigationOnClickListener(v -> onBackPressed());
  }

  @OnClick(R.id.btn_dialog_1)
  protected void onBtnDialog1Click() {
    DialogUtils.showInfoDialog(this, getString(R.string.text_button_color_in_dialog_info), null, null);
  }

  @OnClick(R.id.btn_dialog_2)
  protected void onBtnDialog2Click() {
    AlertDialog dialog = new AlertDialog.Builder(DialogActivity.this)
        .setTitle(getString(R.string.text_custom_dialog))
        .setView(R.layout.custom_dialog)
        .setPositiveButton(R.string.text_ok, null)
        .setNegativeButton(R.string.text_cancel, null)
        .create();
    dialog.setOnShowListener(d -> {
      AlertDialog alertDialog = (AlertDialog) d;
      Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
      CheckBox checkBox = (CheckBox) alertDialog.findViewById(R.id.checkbox);
      checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> positiveButton.setEnabled(isChecked));
      positiveButton.setEnabled(checkBox.isChecked());
    });
    dialog.show();
  }
}
