/**
 * Copyright (C) 2015+ furplag (https://github.com/furplag/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package statics.jp.furplag.sastruts.extension.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import jp.furplag.struts.initializer.dto.PropsDto;
import jp.furplag.struts.initializer.service.PropDtoService;
import jp.furplag.util.commons.StringUtils;

import org.apache.struts.util.LabelValueBean;
import org.seasar.framework.container.SingletonS2Container;

public class ResourceUtils extends jp.furplag.util.ResourceUtils {

  protected static PropsDto propsDto;
  static {
    try {
      propsDto = SingletonS2Container.getComponent(PropsDto.class);
    } catch (Exception e) {
      propsDto = new PropsDto();
    }
  }

  protected static final String RESOURCE_DEFAULT = "application";

  protected ResourceUtils() {
    super();
  }

  public static String get(final String bundle, final String key, final Object[] arguments, Locale locale, final String defaultString) {
    String ret = propsDto == null ? StringUtils.EMPTY : propsDto.get(bundle, key);
    if (!StringUtils.isBlank(ret)) return MessageFormat.format(StringUtils.defaultString(ret), arguments);

    return jp.furplag.util.ResourceUtils.get(bundle, key, arguments, defaultString, locale);
  }

  public static List<LabelValueBean> gets(final String key) {
    return gets(key, null, false);
  }

  public static List<LabelValueBean> gets(final String key, final boolean reverse) {
    return gets(key, null, reverse);
  }

  public static List<LabelValueBean> gets(final String key, final String orderKey) {
    return gets(key, orderKey, false);
  }

  public static List<LabelValueBean> gets(final String key, final String orderKey, final boolean reverse) {
    List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
    for (jp.furplag.struts.initializer.dto.PropDto e : propsDto.gets(key, orderKey, reverse)) {
      ret.add(e.toLabelValueBean());
    }

    return ret;
  }

  public static List<LabelValueBean> getsKeyExcludes(final String key, final String... excludes) {
    return getsKeyExcludes(key, null, false, excludes);
  }

  public static List<LabelValueBean> getsKeyExcludes(final String key, final String orderKey, final String... excludes) {
    return getsKeyExcludes(key, orderKey, false, excludes);
  }

  public static List<LabelValueBean> getsKeyExcludes(final String key, final boolean reverse, final String... excludes) {
    return getsKeyExcludes(key, null, reverse, excludes);
  }

  public static List<LabelValueBean> getsKeyExcludes(final String key, final String orderKey, final boolean reverse, final String... excludes) {
    List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
    for (jp.furplag.struts.initializer.dto.PropDto e : propsDto.getKeyExcludes(key, orderKey, reverse, excludes)) {
      ret.add(e.toLabelValueBean());
    }

    return ret;
  }

  public static List<LabelValueBean> getsKeyIncludes(final String key, final String... includes) {
    return getsKeyIncludes(key, null, false, includes);
  }

  public static List<LabelValueBean> getsKeyIncludes(final String key, final String orderKey, final String... includes) {
    return getsKeyIncludes(key, orderKey, false, includes);
  }

  public static List<LabelValueBean> getsKeyIncludes(final String key, final boolean reverse, final String... includes) {
    return getsKeyIncludes(key, null, reverse, includes);
  }

  public static List<LabelValueBean> getsKeyIncludes(final String key, final String orderKey, final boolean reverse, final String... includes) {
    List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
    for (jp.furplag.struts.initializer.dto.PropDto e : propsDto.getKeyIncludes(key, orderKey, reverse, includes)) {
      ret.add(new LabelValueBean(e.value, e.key));
    }

    return ret;
  }

  public static List<LabelValueBean> getsValueExcludes(final String key, final String orderKey, final boolean reverse, final String... excludes) {
    List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
    List<String> l = Arrays.asList(excludes);
    for (jp.furplag.struts.initializer.dto.PropDto e : propsDto.gets(key, orderKey, reverse)) {
      if (!l.contains(e.value)) ret.add(new LabelValueBean(e.value, e.key));
    }

    return ret;
  }

  public static List<LabelValueBean> getsValueIncludes(final String key, final String orderKey, final boolean reverse, final String... includes) {
    List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
    List<String> l = Arrays.asList(includes);
    for (jp.furplag.struts.initializer.dto.PropDto e : propsDto.gets(key, orderKey, reverse)) {
      if (l.contains(e.value)) ret.add(new LabelValueBean(e.value, e.key));
    }

    return ret;
  }

  public static boolean containsKey(final String key, final String searchKey) {
    return getsKeyIncludes(key, null, false, searchKey).size() > 0;
  }

  public static boolean containsValue(final String key, final String value) {
    return getsValueIncludes(key, null, false, value).size() > 0;
  }

  public static void setPropsDto() {
    try {
      if (propsDto != null) propsDto.setProps(SingletonS2Container.getComponent(PropDtoService.class).getProps());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
