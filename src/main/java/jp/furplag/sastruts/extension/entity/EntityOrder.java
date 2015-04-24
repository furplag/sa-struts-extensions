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

import jp.furplag.util.commons.StringUtils;

public class EntityOrder {

  public int order;
  public String columnName;
  public Direction direction;

  enum Direction {
    ASC(0), DESC(1);
    private final int id;
    Direction(int id) {this.id = id;}
    public int getDirection() {return this.id;}
  }

  public EntityOrder(String columnName) {
    this(0, columnName, Direction.ASC);
  }

  public EntityOrder(int order, String columnName) {
    this(order, columnName, Direction.ASC);
  }

  public EntityOrder(int order, String columnName, int descending) {
    this(order, columnName, descending == 1 ? Direction.DESC : Direction.ASC);
  }

  public EntityOrder(int order, String columnName, boolean descending) {
    this(order, columnName, descending ? Direction.DESC : Direction.ASC);
  }

  public EntityOrder(int order, String columnName, Direction direction) {
    this.order = order;
    this.columnName = columnName;
    this.direction = direction;
  }

  @Override
  public String toString() {
    return StringUtils.joinExcludesBlank(new String[]{columnName, direction.toString()}, " ");
  }
}
