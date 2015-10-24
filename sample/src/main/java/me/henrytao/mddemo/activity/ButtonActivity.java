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
import android.view.LayoutInflater;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.henrytao.mddemo.R;
import me.henrytao.mddemo.utils.DialogUtils;

public class ButtonActivity extends BaseLayoutActivity {

  public static Intent newIntent(Context context) {
    return new Intent(context, ButtonActivity.class);
  }

  @Bind(R.id.btn_dialog)
  Button vBtnDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LayoutInflater.from(this).inflate(R.layout.fragment_button, vContainer, true);
    ButterKnife.bind(this);

    vBtnDialog.setOnClickListener(v -> DialogUtils.showInfoDialog(this, getString(R.string.text_button_color_in_dialog_info), null, null));
  }
}
