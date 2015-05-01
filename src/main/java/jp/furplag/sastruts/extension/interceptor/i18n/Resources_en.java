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

public class Resources_en extends ListResourceBundle {

  private static final Object[][] OBJECTS = new Object[][] {
    {"start", "Processing START [{0}#{1}]."},
    {"end", "Complete processing [{0}#{1}] ({2})."},
    {"errors.timeout", "Expired Session."},
    {"errors.auth", "Could not Authenticate."},
    {"errors.permission", "Permission denied."},
    {"errors.permission.config", "Permission denied."},
    {"errors.permission.admin", "Administrators only."},
    {"errors.token", "Duplicate-process occurred."},
    {"errors.fatal", "Fatal Error: Unable to continue to processing."},
    {"errors.throwable", "Fatal Error: Unable to continue to processing."},
    {"errors.stacktrace.overflow", "and more..."}
  };

  protected Object[][] getContents() {
    return OBJECTS;
  }
}
