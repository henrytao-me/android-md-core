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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.CheckBox;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.henrytao.mdcore.utils.AlertDialogBuilder;
import me.henrytao.mdcore.widgets.MdIconToggle;
import me.henrytao.mddemo.R;
import me.henrytao.mddemo.utils.DialogUtils;

public class ButtonActivity extends BaseLayoutActivity {

  public static Intent newIntent(Context context) {
    return new Intent(context, ButtonActivity.class);
  }

  @Bind(R.id.btn_dialog) Button vBtnDialog;

  @Bind(R.id.btn_dialog_2) Button vBtnDialog2;

  @Bind(R.id.toggle_up) MdIconToggle vMdIconToggleUp;

  private int mCount;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LayoutInflater.from(this).inflate(R.layout.fragment_button, vContainer, true);
    ButterKnife.bind(this);

    vBtnDialog.setOnClickListener(v -> DialogUtils.showInfoDialog(this, getString(R.string.text_button_color_in_dialog_info), null, null));
    vBtnDialog2.setOnClickListener(v -> {
      new AlertDialogBuilder(ButtonActivity.this)
          .setTitle(getString(R.string.text_custom_dialog))
          .setView(R.layout.custom_dialog)
          .setPositiveButton(R.string.text_ok, null)
          .setNegativeButton(R.string.text_cancel, null)
          .setOnShowListener(dialog -> {
            AlertDialog alertDialog = (AlertDialog) dialog;
            Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            CheckBox checkBox = (CheckBox) alertDialog.findViewById(R.id.checkbox);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> positiveButton.setEnabled(isChecked));
            positiveButton.setEnabled(checkBox.isChecked());
          })
          .show();
    });

    vMdIconToggleUp.setOnCheckedChangeListener((buttonView, isChecked) -> {
      mCount += 1;
      vMdIconToggleUp.setText(String.valueOf(mCount));
    });
  }
}
