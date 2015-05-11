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
package jp.furplag.struts.initializer.i18n;

import java.util.ListResourceBundle;

public class Resources_ja extends ListResourceBundle {

  private static final Object[][] OBJECTS = new Object[][] {
    {"end", "システム起動処理を終了しました ({0})。"},
    {"fatal", "システム起動処理中にエラーが発生しました。外部リソースは取得されていません。"},
    {"fatal.component", "コンポーネント [{0}] が取得できません。 [src/main/resources/app.dicon] を確認してください。"},
    {"fatal.sql", "データソースにアクセスできません。 [src/main/resources/customizer.dicon, src/main/resources/jdbc.dicon, src/main/resources/s2jdbc.dicon] および [src/main/webapp/lib] を確認してください。"},
    {"fatal.any", "[src/main/webapp/WEB-INF/web.xml, src/main/resources/*.dicon] を確認してください。"},
    {"inactive", "外部リソースの取得をスキップします。"},
    {"result", "{0}取得した外部リソースは {1} 件です。{2}"},
    {"start", "システム起動処理を開始します。"}
  };

  protected Object[][] getContents() {
    return OBJECTS;
  }
}
