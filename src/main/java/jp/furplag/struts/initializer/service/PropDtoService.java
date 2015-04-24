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
package jp.furplag.struts.initializer.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jp.furplag.struts.initializer.dto.PropDto;
import jp.furplag.struts.initializer.service.entity.PropService;
import jp.furplag.util.commons.StringUtils;

public class PropDtoService extends PropService {

  public Map<String, Map<String, PropDto>> getProps() {
    Map<String, Map<String, PropDto>> ret = new ConcurrentHashMap<String, Map<String, PropDto>>();
    Map<String, PropDto> keys = null;
    String key = "";
    for (PropDto e : finder(PropDto.class, new PropDto().getWheresIncludes("deleted"), "id, ordinal, key, value", null, null)) {
      System.out.println(e);
      if (!key.equalsIgnoreCase(e.id)) {
        keys = new ConcurrentHashMap<String, PropDto>();
      }
      keys.put(StringUtils.flatten(e.key), e);
      ret.put(StringUtils.flatten(e.id), keys);
      key = e.id;
    }

    return ret;
  }
}
