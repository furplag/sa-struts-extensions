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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.furplag.struts.initializer.dto.comparator.PropDtoComparator;
import jp.furplag.util.commons.StringUtils;

import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;

@Component(instance = InstanceType.APPLICATION)
public class PropsDto implements Serializable {

  private static final long serialVersionUID = 1L;

  public PropsDto() {}

  private Map<String, Map<String, PropDto>> props;

  public Map<String, Map<String, PropDto>> getProps() {
    return props;
  }

  public void setProps(Map<String, Map<String, PropDto>> props) {
    this.props = props;
  }

  public String get(String id, String key) {
    if (props == null) return StringUtils.EMPTY;
    Map<String, PropDto> keys = props.get(StringUtils.flatten(id));
    if (keys == null) return StringUtils.EMPTY;
    if (!keys.containsKey(StringUtils.flatten(key))) return StringUtils.EMPTY;

    return StringUtils.emptyToSafely(keys.get(StringUtils.flatten(key)).value);
  }

  public List<PropDto> gets(String id) {
    return gets(id, null, false, null, null);
  }

  public List<PropDto> gets(String id, String orderKey) {
    return gets(id, orderKey, false, null, null);
  }

  public List<PropDto> gets(String id, boolean reverse) {
    return gets(id, null, reverse, null, null);
  }

  public List<PropDto> gets(String id, String orderKey, boolean reverse) {
    return gets(id, orderKey, reverse, null, null);
  }

  public List<PropDto> getKeyExcludes(String id, String ... excludes) {
    return gets(id, null, false, null, excludes);
  }

  public List<PropDto> getKeyExcludes(String id, String orderKey, String ... excludes) {
    return gets(id, orderKey, false, null, excludes);
  }

  public List<PropDto> getKeyExcludes(String id, boolean reverse, String ... excludes) {
    return gets(id, null, reverse, null, excludes);
  }

  public List<PropDto> getKeyExcludes(String id, String orderKey, boolean reverse, String ... excludes) {
    return gets(id, orderKey, reverse, null, excludes);
  }

  public List<PropDto> getKeyIncludes(String id, String ... includes) {
    return gets(id, null, false, includes, null);
  }

  public List<PropDto> getKeyIncludes(String id, String orderKey, String ... includes) {
    return gets(id, orderKey, false, includes, null);
  }

  public List<PropDto> getKeyIncludes(String id, boolean reverse, String ... includes) {
    return gets(id, null, reverse, includes, null);
  }

  public List<PropDto> getKeyIncludes(String id, String orderKey, boolean reverse, String ... includes) {
    return gets(id, orderKey, reverse, includes, null);
  }

  public List<PropDto> getValueExcludes(String id, String ... excludes) {
    return gets(id, null, false, null, excludes);
  }

  public List<PropDto> getValueExcludes(String id, String orderKey, String ... excludes) {
    return gets(id, orderKey, false, null, excludes);
  }

  public List<PropDto> getValueExcludes(String id, boolean reverse, String ... excludes) {
    return gets(id, null, reverse, null, excludes);
  }

  public List<PropDto> getValueExcludes(String id, String orderKey, boolean reverse, String ... excludes) {
    return gets(id, orderKey, reverse, null, excludes);
  }

  public List<PropDto> getValueIncludes(String id, String ... includes) {
    return gets(id, null, false, includes, null, false);
  }

  public List<PropDto> getValueIncludes(String id, String orderKey, String ... includes) {
    return gets(id, orderKey, false, includes, null, false);
  }

  public List<PropDto> getValueIncludes(String id, boolean reverse, String ... includes) {
    return gets(id, null, reverse, includes, null, false);
  }

  public List<PropDto> getValueIncludes(String id, String orderKey, boolean reverse, String ... includes) {
    return gets(id, orderKey, reverse, includes, null, false);
  }

  private List<PropDto> gets(String id, String orderKey, boolean reverse, String[] includes, String[] excludes) {
    return gets(id, orderKey, reverse, includes, null, true);
  }

  private List<PropDto> gets(String id, String orderKey, boolean reverse, String[] includes, String[] excludes, boolean isKey) {
    List<PropDto> ret = new ArrayList<PropDto>();
    if (props == null) return ret;
    if (!props.containsKey(StringUtils.flatten(id))) return ret;
    List<String> includeList = Arrays.asList(includes == null ? new String[]{} : includes);
    List<String> excludeList = Arrays.asList(excludes == null ? new String[]{} : excludes);
    for (PropDto e : props.get(StringUtils.flatten(id)).values()) {
      if (includeList.size() < 1) {
        if (!excludeList.contains(isKey ? e.key : e.value)) ret.add(e);
      } else {
        if (includeList.contains(isKey ? e.key : e.value) && !excludeList.contains(isKey ? e.key : e.value)) ret.add(e);
      }
    }
    Collections.sort(ret, new PropDtoComparator(orderKey, reverse));

    return ret;
  }

  public int getCount() {
    if (props == null) return 0;
    int ret = 0;
    for (Entry<String, Map<String, PropDto>> e : props.entrySet()) {
      if (e != null && e.getValue() != null) ret += e.getValue().size();
    }

    return ret;
  }
}
