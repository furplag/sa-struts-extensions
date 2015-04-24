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
package jp.furplag.struts.initializer.dto.comparator;

import java.util.Comparator;

import jp.furplag.struts.initializer.dto.PropDto;
import jp.furplag.util.commons.StringUtils;

public class PropDtoComparator implements Comparator<PropDto> {

  private String key;
  private boolean reverse = false;

  public PropDtoComparator() {
    this(null);
  }

  public PropDtoComparator(boolean reverse) {
    this(null, reverse);
  }

  public PropDtoComparator(String key) {
    this(key, false);
  }

  public PropDtoComparator(String key, boolean reverse) {
    this.key = StringUtils.isBlank(key) ? "ordinal" : key;
    this.reverse = reverse;
  }

  public int compare(PropDto b1, PropDto b2) {
    if (!b1.containsField(key)) return 0;
    if ("ordinal".equals(key)) return reverse ? b2.ordinal.compareTo(b1.ordinal) : b1.ordinal.compareTo(b2.ordinal);
    if ("value".equals(key)) return reverse ? b2.value.compareTo(b1.value) : b1.value.compareTo(b2.value);
    if ("key".equals(key)) return reverse ? b2.key.compareTo(b1.key) : b1.key.compareTo(b2.key);

    return 0;
  }
}
