/**
 * Copyright (C) 2015+ furplag (https://github.com/furplag/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.furplag.sastruts.extension.interceptor.i18n;

import java.util.ListResourceBundle;

public class Resources_ja extends ListResourceBundle {

  private static final Object[][] OBJECTS = new Object[][] {
    {"start", "[{0}#{1}] 開始."},
    {"end", "[{0}#{1}] 完了 ({2})."},
    {"errors.timeout", "セッションの有効期限が切れました。"},
    {"errors.auth", "認証できません。"},
    {"errors.permission", "権限がありません。"},
    {"errors.permission.config", "システム設定の管理権限がありません。"},
    {"errors.permission.admin", "システム管理権限がありません。"},
    {"errors.token", "多重処理を検出しました。"},
    {"errors.fatal", "システムエラー： 処理を続行することができません。"},
    {"errors.throwable", "システムエラー： 処理を続行することができません。"},
    {"errors.stacktrace.overflow", "and more..."}
  };

  protected Object[][] getContents() {
    return OBJECTS;
  }
}
