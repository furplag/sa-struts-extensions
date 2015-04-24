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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import jp.furplag.sastruts.extension.persistence.DisplayName;
import jp.furplag.sastruts.extension.persistence.NoPrettify;
import jp.furplag.sastruts.extension.persistence.SingleLinear;
import jp.furplag.util.commons.StringUtils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.math.NumberUtils;
import org.seasar.framework.beans.util.Beans;
import org.seasar.framework.beans.util.CreateAndCopy;

public abstract class AbstractEntity {

  protected static final String PARAM_SEPARATOR = ",";

  public enum YepNope {
    YEP(1), NOPE(0);
    private final int id;
    YepNope(int id) {
      this.id = id;
    }
    public int getYepNope() {
      return id;
    }
    public static boolean contains(int id) {
      for (YepNope e : YepNope.values()) {
        if (e.id == id) return true;
      }
      return false;
    }
    public static boolean contains(YepNope toggle) {
      for (YepNope e : YepNope.values()) {
        if (e.equals(toggle)) return true;
      }
      return false;
    }
    public static YepNope get(int id) {
      for (YepNope e : YepNope.values()) {
        if (e.id == id) return e;
      }
      return null;
    }
    public static YepNope yepNope(Object o) {
      return o == null ? NOPE : get(NumberUtils.toInt(o.toString(), 0));
    }
    public static int yep() {
      return YEP.id;
    }
    public static int nope() {
      return NOPE.id;
    }
  }
  public enum WheresMode {
    ALL(0), ID(1), INCLUDES(2), EXCLUDES(3);
    private final int id;
    WheresMode(int id) {
      this.id = id;
    }
    public int getWheresMode() {
      return id;
    }
    public static boolean contains(int id) {
      for (WheresMode e : WheresMode.values()) {
        if (e.id == id) return true;
      }
      return false;
    }
    public static boolean contains(WheresMode wheresMode) {
      for (WheresMode e : WheresMode.values()) {
        if (e.equals(wheresMode)) return true;
      }
      return false;
    }
    public static WheresMode get(int id) {
      for (WheresMode e : WheresMode.values()) {
        if (e.id == id) return e;
      }
      return null;
    }
  };

  protected AbstractEntity() {}

  public boolean containsField(String propertyNames) {
    if (StringUtils.isSimilarToBlank(propertyNames)) return false;
    try {
      Field f = this.getClass().getField(propertyNames);
      return f != null;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  public Map<String, Object> getWheres() {
    return getWheres(true, WheresMode.ALL, new String[]{});
  }

  public Map<String, Object> getWheres(boolean normalize) {
    return getWheres(normalize, WheresMode.ALL, new String[]{});
  }

  public Map<String, Object> getWheres(WheresMode mode) {
    return getWheres(true, mode, new String[]{});
  }

  public Map<String, Object> getWheres(int mode) {
    return getWheres(true, WheresMode.contains(mode) ? WheresMode.get(mode) : WheresMode.ALL, new String[]{});
  }

  public Map<String, Object> getWheres(String... propertyNames) {
    return getWheres(true, WheresMode.INCLUDES, propertyNames);
  }

  public Map<String, Object> getWheres(boolean normalize, WheresMode mode) {
    return getWheres(normalize, mode, new String[]{});
  }

  public Map<String, Object> getWheres(boolean normalize, int mode, String... propertyNames) {
    return getWheres(normalize, WheresMode.contains(mode) ? WheresMode.get(mode) : WheresMode.ALL, propertyNames);
  }

  public Map<String, Object> getWheres(boolean normalize, WheresMode mode, String... propertyNames) {
    Map<String, Object> ret = new HashMap<String, Object>();
    if (!WheresMode.contains(mode)) return ret;
    List<String> wheres = propertyNames != null && propertyNames.length > 0 ? Arrays.asList(propertyNames) : new ArrayList<String>();
    for (Field f : this.getClass().getFields()) {
      if (WheresMode.ID == mode && !f.isAnnotationPresent(Id.class)) continue;
      if (WheresMode.INCLUDES == mode && !wheres.contains(f.getName())) continue;
      if (WheresMode.EXCLUDES == mode && wheres.contains(f.getName())) continue;
      try {
        Object o = f.get(this);
        if (o == null) continue;
        if (StringUtils.isSimilarToBlank(o.toString())) continue;
        ret.put(f.getName(), normalize && f.getType() == String.class ? StringUtils.normalize(o.toString()) : o);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return ret;
  }

  public Map<String, Object> getWheresIdentifir() {
    return getWheres(true, WheresMode.ID, new String[]{});
  }

  public Map<String, Object> getWheresIncludes(String... propertyNames) {
    return getWheres(true, WheresMode.INCLUDES, propertyNames);
  }

  public Map<String, Object> getWheresExcludes(String... propertyNames) {
    return getWheres(true, WheresMode.EXCLUDES, propertyNames);
  }

  public String getOrderDefault(boolean descending) {
    List<EntityOrder> orders = new ArrayList<EntityOrder>();
    for (Field f : this.getClass().getFields()) {
      if (!f.isAnnotationPresent(Column.class)) continue;
      if (f.isAnnotationPresent(Transient.class)) continue;
      orders.add(new EntityOrder((orders.size() + 1) + (f.isAnnotationPresent(Id.class) ? 1 : 1000), f.getName(), descending));
    }
    Collections.sort(orders, new EntityOrderComparator());

    return orders.size() < 1 ? null : StringUtils.joinExcludesBlank(orders.toArray(new String[orders.size()]), ", ");
  }

  public boolean isValid(boolean isInsert) {
    return validates(isInsert).size() < 1;
  }

  public Map<String, FieldDescription> validates(boolean isInsert) {
    Map<String, FieldDescription> ret = new HashMap<String, FieldDescription>();
    for (Field f : this.getClass().getFields()) {
      FieldDescription e = validate(f, isInsert);
      if (!e.isValid()) ret.put(f.getName(), e);
    }

    return ret;
  }

  public FieldDescription validate(Field f, boolean isInsert) {
    if (f == null) return new FieldDescription();
    if (!f.isAnnotationPresent(Column.class)) return new FieldDescription();
    if (f.isAnnotationPresent(Transient.class)) return new FieldDescription();
    Object obj;
    try {
      obj = f.get(this);
    } catch (Exception e) {
      e.printStackTrace();
      return new FieldDescription();
    }
    if (isInsert && obj == null && f.isAnnotationPresent(GeneratedValue.class)) {
      return new FieldDescription();
    } else if (isInsert && !f.getAnnotation(Column.class).insertable()) {
      return new FieldDescription();
    } else if (!isInsert && !f.getAnnotation(Column.class).updatable()) {
      return new FieldDescription();
    } else if (f.isAnnotationPresent(Id.class) && !f.isAnnotationPresent(GeneratedValue.class)) {
      if (!(obj != null && StringUtils.isSimilarToBlank(obj.toString()))) return new FieldDescription(false, "required", "required");
    } else if (!f.getAnnotation(Column.class).nullable()) {
      if (!(obj != null && StringUtils.isSimilarToBlank(obj.toString()))) return new FieldDescription(false, "required", "required");
    }
    if (obj != null && StringUtils.byteLength(obj.toString()) > f.getAnnotation(Column.class).length()) return new FieldDescription(false, "overflow", "length:" + f.getAnnotation(Column.class).length() + "(value:" + obj.toString().length() + ")");

    if (obj instanceof Number) {
      int precision = f.getAnnotation(Column.class).precision();
      int scale = f.getAnnotation(Column.class).scale();
      String toString = obj.toString().replaceAll("^\\.", "0.").replaceAll("^-\\.", "-0.").replaceAll("\\.0+$", "");
      if (precision != 0 && scale != 0 && precision > scale) {
        if (scale > 0 && toString.indexOf("\\.") > -1 && toString.replaceAll("^-?\\d+\\.", "").length() > scale) return new FieldDescription(false, "overflow", "scale:" + scale + "(value:" + toString + ")");
        if (precision < toString.replaceAll("\\.", "").length()) return new FieldDescription(false, "overflow", "precision:" + precision + "(value:" + toString + ")");
        if (scale == 0 && toString.indexOf("\\.") > -1) return new FieldDescription(false, "numbertype", "numeric only");
      }
      if (f.isAnnotationPresent(jp.furplag.sastruts.extension.persistence.Toggle.class) && !YepNope.contains(NumberUtils.toInt(toString, -1))) return new FieldDescription(false, "toggle", "0 or 1 only.");
    }

    return new FieldDescription();
  }

  public void normalize() {
    if (this.getClass().isAnnotationPresent(NoPrettify.class)) return;
    Field[] fields = this.getClass().getFields();
    for (Field f : fields) {
      if (!f.getType().equals(String.class)) continue;
      if (f.isAnnotationPresent(NoPrettify.class)) continue;
      try {
        if (f.get(this) == null) continue;
        String value = f.get(this).toString();
        if (StringUtils.isSimilarToBlank(value)) {
          f.set(this, null);
          continue;
        } else if (Pattern.compile("\\r?\\n").matcher(value).matches() && f.isAnnotationPresent(SingleLinear.class)) {
          value = value.replaceAll("\\r?\\n", "");
        }
        f.set(this, StringUtils.normalize(value));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  public <T> T morph(Class<T> destClass) {
    return morph(destClass, WheresMode.ALL);
  }

  public <T> T morph(Class<T> destClass, WheresMode mode, String... propertyNames) {
    if (destClass == null) return null;
    CreateAndCopy<T> ret = Beans.createAndCopy(destClass, this);
    try {
      if (WheresMode.ID == mode) {
        Map<String, Object> identifir = getWheresIdentifir();
        if (identifir != null && identifir.size() > 0) ret = ret.includes(StringUtils.join(identifir.keySet(), ","));
      }
      if (WheresMode.INCLUDES == mode && propertyNames != null && propertyNames.length > 0) ret = ret.includes(propertyNames);
      if (WheresMode.EXCLUDES == mode && propertyNames != null && propertyNames.length > 0) ret = ret.excludes(propertyNames);
      return ret.execute();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public <T> T morphIdentifir(Class<T> destClass) {
    return morph(destClass, WheresMode.ID);
  }

  public <T> T morphIncludes(Class<T> destClass, String... propertyNames) {
    return morph(destClass, WheresMode.INCLUDES, propertyNames);
  }

  public <T> T morphExcludes(Class<T> destClass, String... propertyNames) {
    return morph(destClass, WheresMode.EXCLUDES, propertyNames);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }

  public String csvify() {
    return csvify(false);
  }

  public String csvify(boolean headerRow) {
    StringBuilder sb = new StringBuilder();
    boolean first = true;
    try {
      for (Field f : this.getClass().getFields()) {
        sb.append(first ? "\"" : ",\"").append(headerRow ? f.isAnnotationPresent(DisplayName.class) ? f.getAnnotation(DisplayName.class).value() : StringUtils.flatten(f.getName()) : f.get(this) != null ? f.get(this).toString() : "").append("\"");
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    return sb.toString();
  }

  protected String getDisplayName(String propertyName) {
    if (StringUtils.isSimilarToBlank(propertyName) && this.getClass().isAnnotationPresent(DisplayName.class)) return this.getClass().getAnnotation(DisplayName.class).value();
    if (StringUtils.isSimilarToBlank(propertyName) && this.getClass().isAnnotationPresent(Table.class) && StringUtils.isSimilarToBlank(this.getClass().getAnnotation(Table.class).name())) return this.getClass().getAnnotation(Table.class).name();
    if (StringUtils.isSimilarToBlank(propertyName)) return this.getClass().getSimpleName();
    try {
      Field f = this.getClass().getField(propertyName);
      return f.isAnnotationPresent(DisplayName.class) ? f.getAnnotation(DisplayName.class).value() : StringUtils.flatten(f.getName());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}
