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
package statics.jp.furplag.sastruts.extension.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.furplag.struts.initializer.dto.PropsDto;
import jp.furplag.struts.initializer.service.PropDtoService;
import jp.furplag.util.commons.StringUtils;

import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;
import org.seasar.framework.container.SingletonS2Container;

public class ResourceUtils {

  protected static PropsDto propsDto;
  static {
    try {
      propsDto = SingletonS2Container.getComponent(PropsDto.class);
    } catch (Exception e) {
      propsDto = new PropsDto();
    }
  }

  protected static final String RESOURCE_DEFAULT = "application";

  protected ResourceUtils() {}

  public static String getProp(final String key) {
    return getResource(null, key, null, null);
  }

  public static String getProp(final String bundle, final String key) {
    return getResource(bundle, key, null, null);
  }

  public static String getProp(final String key, final Object[] args) {
    return getResource(null, key, args, null);
  }

  public static String getProp(final String bundle, final String key, final Object[] args) {
    return getResource(bundle, key, args, null);
  }

  public static String getProp(final String bundle, final String key, final String defaultString) {
    return getResource(bundle, key, null, defaultString);
  }

  public static String getProp(final String bundle, final String key, final Object[] args, final String defaultString) {
    String ret = getResource(bundle, key, args, defaultString);
    return StringUtils.isBlank(ret) ? defaultString : ret;
  }

  private static String getResource(final String bundle, final String key, final Object[] args, final String defaultString) {
    String ret = propsDto == null ? StringUtils.EMPTY : propsDto.get(bundle, key);
    if (!StringUtils.isBlank(ret)) return ret;
    if (StringUtils.isBlank(key)) return StringUtils.isBlank(defaultString) ? StringUtils.EMPTY : defaultString;
    MessageResources resource = MessageResources.getMessageResources(StringUtils.isSimilarToBlank(bundle) ? RESOURCE_DEFAULT : bundle);
    if (resource == null) return StringUtils.isBlank(defaultString) ? StringUtils.EMPTY : defaultString;
    ret = resource.getMessage(key, args);

    return StringUtils.isBlank(ret) ? StringUtils.isBlank(defaultString) ? StringUtils.EMPTY : defaultString : ret;
  }

  public static List<LabelValueBean> getDdl(final String key) {
    return getDdl(key, null, false);
  }

  public static List<LabelValueBean> getDdl(final String key, final boolean reverse) {
    return getDdl(key, null, reverse);
  }

  public static List<LabelValueBean> getDdl(final String key, final String orderKey) {
    return getDdl(key, orderKey, false);
  }

  public static List<LabelValueBean> getDdl(final String key, final String orderKey, final boolean reverse) {
    List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
    for (jp.furplag.struts.initializer.dto.PropDto e : propsDto.gets(key, orderKey, reverse)) {
      ret.add(e.toLabelValueBean());
    }

    return ret;
  }

  public static List<LabelValueBean> getDdlKeyExcludes(final String key, final String... excludes) {
    return getDdlKeyExcludes(key, null, false, excludes);
  }

  public static List<LabelValueBean> getDdlKeyExcludes(final String key, final String orderKey, final String... excludes) {
    return getDdlKeyExcludes(key, orderKey, false, excludes);
  }

  public static List<LabelValueBean> getDdlKeyExcludes(final String key, final boolean reverse, final String... excludes) {
    return getDdlKeyExcludes(key, null, reverse, excludes);
  }

  public static List<LabelValueBean> getDdlKeyExcludes(final String key, final String orderKey, final boolean reverse, final String... excludes) {
    List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
    for (jp.furplag.struts.initializer.dto.PropDto e : propsDto.getKeyExcludes(key, orderKey, reverse, excludes)) {
      ret.add(e.toLabelValueBean());
    }

    return ret;
  }

  public static List<LabelValueBean> getDdlKeyIncludes(final String key, final String... includes) {
    return getDdlKeyExcludes(key, null, false, includes);
  }

  public static List<LabelValueBean> getDdlKeyIncludes(final String key, final String orderKey, final String... includes) {
    return getDdlKeyExcludes(key, orderKey, false, includes);
  }

  public static List<LabelValueBean> getDdlKeyIncludes(final String key, final boolean reverse, final String... includes) {
    return getDdlKeyExcludes(key, null, reverse, includes);
  }

  public static List<LabelValueBean> getDdlKeyIncludes(final String key, final String orderKey, final boolean reverse, final String... includes) {
    List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
    for (jp.furplag.struts.initializer.dto.PropDto e : propsDto.getKeyIncludes(key, orderKey, reverse, includes)) {
      ret.add(new LabelValueBean(e.value, e.key));
    }

    return ret;
  }

  public static List<LabelValueBean> getDdlValueExcludes(final String key, final String orderKey, final boolean reverse, final String... excludes) {
    List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
    List<String> l = Arrays.asList(excludes);
    for (jp.furplag.struts.initializer.dto.PropDto e : propsDto.gets(key, orderKey, reverse)) {
      if (!l.contains(e.value)) ret.add(new LabelValueBean(e.value, e.key));
    }

    return ret;
  }

  public static List<LabelValueBean> getDdlValueIncludes(final String key, final String orderKey, final boolean reverse, final String... includes) {
    List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
    List<String> l = Arrays.asList(includes);
    for (jp.furplag.struts.initializer.dto.PropDto e : propsDto.gets(key, orderKey, reverse)) {
      if (l.contains(e.value)) ret.add(new LabelValueBean(e.value, e.key));
    }

    return ret;
  }

  public static boolean containsKey(final String key, final String searchKey) {
    return getDdlKeyIncludes(key, null, false, searchKey).size() > 0;
  }

  public static boolean containsValue(final String key, final String value) {
    return getDdlValueIncludes(key, null, false, value).size() > 0;
  }

  public static void setPropsDto() {
    try {
      if (propsDto != null) propsDto.setProps(SingletonS2Container.getComponent(PropDtoService.class).getProps());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
