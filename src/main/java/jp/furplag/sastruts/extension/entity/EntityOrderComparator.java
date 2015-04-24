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
package jp.furplag.sastruts.extension.entity;

import java.util.Comparator;

public class EntityOrderComparator implements Comparator<EntityOrder> {

  private boolean descending = false;

  public EntityOrderComparator() {}

  public EntityOrderComparator(boolean descending) {
    this.descending = descending;
  }

  @Override
  public int compare(EntityOrder o1, EntityOrder o2) {
    return Integer.valueOf((descending ? o2 : o1).order).compareTo(Integer.valueOf(((descending ? o1 : o2)).order));
  }
}
