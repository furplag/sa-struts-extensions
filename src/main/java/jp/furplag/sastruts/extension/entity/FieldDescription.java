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

public class FieldDescription {

  public FieldDescription(boolean valid, String cause, String hinting) {
    this.valid = valid;
    this.cause = cause;
    this.hinting = hinting;
  }

  public FieldDescription(boolean valid) {
    this(valid, null, null);
  }

  public FieldDescription() {
    this(true, null, null);
  }

  private boolean valid = true;

  private String cause;

  private String hinting;

  public boolean isValid() {
    return valid;
  }

  public String getCause() {
    return cause;
  }

  public String getHinting() {
    return hinting;
  }

  public void setValid(boolean valid) {
    this.valid = valid;
  }

  public void setCause(String cause) {
    this.cause = cause;
  }

  public void setHinting(String hinting) {
    this.hinting = hinting;
  }
}
