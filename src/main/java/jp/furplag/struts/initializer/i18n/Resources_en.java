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

public class Resources_en extends ListResourceBundle {

  private static final Object[][] OBJECTS = new Object[][] {
    {"end", "Webapp initialization end. (cost:{0}msec)"},
    {"fatal", "Webapp initialization failed, no additional properties defined."},
    {"fatal.component", "Component [{0}] not found. Check settings [src/main/resources/app.dicon] and configure."},
    {"fatal.sql", "Unable to access Data Sources. Check settings [src/main/resources/customizer.dicon, src/main/resources/jdbc.dicon, src/main/resources/s2jdbc.dicon] and dependencies [src/main/webapp/lib] and configure."},
    {"fatal.any", "Check settings [src/main/webapp/WEB-INF/web.xml, src/main/resources/*.dicon], and see below if you need more information."},
    {"inactive", "Skip retrieving of external properties."},
    {"result", "{0} {1} records exist.{2}"},
    {"start", "Webapp initialization start."}
  };

  protected Object[][] getContents() {
    return OBJECTS;
  }
}
