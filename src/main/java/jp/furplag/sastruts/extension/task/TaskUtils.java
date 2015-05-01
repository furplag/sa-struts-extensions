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
package jp.furplag.sastruts.extension.task;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

public class TaskUtils {

  protected TaskUtils() {}

  private static final String EXPRESSION_DEFAULT = "0 0 0 * * ?";

  public static String getDefaultExpression() {
    return EXPRESSION_DEFAULT;
  }

  public static String getInactiveExpression() {
    return StringUtils.join(new Object[]{EXPRESSION_DEFAULT, DateTime.now().minusYears(1000).getYear()}, " ");
  }
}
