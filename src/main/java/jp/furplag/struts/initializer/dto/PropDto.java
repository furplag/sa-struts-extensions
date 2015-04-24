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
package jp.furplag.struts.initializer.dto;

import java.io.Serializable;

import javax.persistence.Entity;

import org.apache.struts.util.LabelValueBean;

import jp.furplag.struts.initializer.entity.Prop;

@Entity
public class PropDto extends Prop implements Serializable {

  private static final long serialVersionUID = 7275861144888090331L;

  public PropDto() {this.deleted = YepNope.nope();}

  public PropDto(boolean deleted) {this.deleted = deleted ? YepNope.yep() : YepNope.nope();}

  public LabelValueBean toLabelValueBean() {
    return new LabelValueBean(this.value, this.key);
  }
}
