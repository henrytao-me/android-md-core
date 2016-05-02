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

package me.henrytao.mddemo.config;

/**
 * Created by henrytao on 5/2/16.
 */
public class Constants {

  public interface Extra {

    String IS_MD_CORE_ENABLED = "IS_MD_CORE_ENABLED";
  }

  public interface Timer {

    int LONG = 600;
    int MEDIUM = 400;
    int SHORT = 200;
  }
}
