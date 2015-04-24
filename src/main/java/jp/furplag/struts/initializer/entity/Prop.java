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
package jp.furplag.struts.initializer.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import jp.furplag.sastruts.extension.entity.AbstractEntity;
import jp.furplag.sastruts.extension.persistence.DisplayName;
import jp.furplag.sastruts.extension.persistence.SingleLinear;
import jp.furplag.sastruts.extension.persistence.Toggle;

import org.seasar.framework.container.annotation.tiger.Property;

@Entity
@MappedSuperclass
@Table(name = "config")
@DisplayName("コード")
public class Prop extends AbstractEntity {

  @Id
  @Property
  @Column(name="id", unique = true, nullable = false, length = 100)
  @SingleLinear
  public String id;

  @Id
  @Property
  @Column(name="name", unique = true, nullable = false, length = 100)
  @SingleLinear
  public String key;

  @Property
  @Lob
  @Column(name="description", nullable = false, length = 65535)
  public String value;

  @Property
  @Column(name="ordinal", nullable = false, precision = 4)
  public Integer ordinal;

  @Property
  @Column(name="deleted", nullable = false, precision = 1)
  @Toggle
  public Integer deleted;

  @Property
  @Column(name="regist", nullable = false, length = 100, updatable = false)
  public String regist;

  @Property
  @Column(name="register", nullable = false, length = 100, updatable = false)
  public String register;

  @Property
  @Column(name="registered", nullable = false, updatable = false)
  public Timestamp registered;

  @Property
  @Column(name="amend", nullable = false, length = 100)
  public String amend;

  @Property
  @Column(name="amender", nullable = false, length = 100)
  public String amender;

  @Property
  @Column(name="updated", nullable = false)
  public Timestamp updated;
}
