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

package me.henrytao.mdcore.utils;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.Locale;

/**
 * Created by henrytao on 11/10/15.
 */
public class Ln {

  private static final String TAG = "mdcore";

  public static boolean DEBUG = false;

  public static void d(String format, Object... args) {
    d(TAG, format, args);
  }

  public static void d(String tag, Throwable throwable) {
    if (DEBUG) {
      Log.d(tag, getStackTraceString(throwable), throwable);
    }
  }

  public static void d(Throwable throwable) {
    d(TAG, throwable);
  }

  public static void e(String format, Object... args) {
    e(TAG, format, args);
  }

  public static void e(String tag, Throwable throwable) {
    if (DEBUG) {
      Log.e(tag, getStackTraceString(throwable), throwable);
    }
  }

  public static void e(Throwable throwable) {
    e(TAG, throwable);
  }

  public static void i(String format, Object... args) {
    i(TAG, format, args);
  }

  public static void i(String tag, Throwable throwable) {
    if (DEBUG) {
      Log.i(tag, getStackTraceString(throwable), throwable);
    }
  }

  public static void i(Throwable throwable) {
    if (DEBUG) {
      i(TAG, throwable);
    }
  }

  public static void v(String format, Object... args) {
    v(TAG, format, args);
  }

  public static void v(String tag, Throwable throwable) {
    if (DEBUG) {
      Log.v(tag, getStackTraceString(throwable), throwable);
    }
  }

  public static void v(Throwable throwable) {
    v(TAG, throwable);
  }

  public static void w(String format, Object... args) {
    w(TAG, format, args);
  }

  public static void w(String tag, Throwable throwable) {
    if (DEBUG) {
      Log.w(tag, getStackTraceString(throwable), throwable);
    }
  }

  public static void w(Throwable throwable) {
    w(TAG, throwable);
  }

  private static void d(String tag, String format, Object... args) {
    if (DEBUG) {
      Log.d(tag, String.format(Locale.US, format, args));
    }
  }

  private static void e(String tag, String format, Object... args) {
    if (DEBUG) {
      Log.e(tag, String.format(Locale.US, format, args));
    }
  }

  private static String getStackTraceString(Throwable tr) {
    if (tr == null) {
      return "";
    }

    // This is to reduce the amount of log spew that apps do in the non-error
    // condition of the network being unavailable.
    Throwable t = tr;
    while (t != null) {
      if (t instanceof UnknownHostException) {
        return "";
      }
      t = t.getCause();
    }

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    tr.printStackTrace(pw);
    pw.flush();
    return sw.toString();
  }

  private static void i(String tag, String format, Object... args) {
    if (DEBUG) {
      Log.i(tag, String.format(Locale.US, format, args));
    }
  }

  private static void v(String tag, String format, Object... args) {
    if (DEBUG) {
      Log.v(tag, String.format(Locale.US, format, args));
    }
  }

  private static void w(String tag, String format, Object... args) {
    if (DEBUG) {
      Log.w(tag, String.format(Locale.US, format, args));
    }
  }
}
