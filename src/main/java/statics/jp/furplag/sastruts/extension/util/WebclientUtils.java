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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.furplag.util.WebInspector;
import jp.furplag.util.commons.StringUtils;

import org.joda.time.DateTime;
import org.seasar.struts.util.RequestUtil;

public class WebclientUtils {

  protected static HttpServletRequest request;
  static {
    request = RequestUtil.getRequest();
  }

  protected static final String IE_NAME = "internetexplorer";

  protected static final String SAFARI_NAME = "safari";

  protected WebclientUtils() {}

  public static Map<String, String> getRequest() {
    return WebInspector.get(request);
  }

  public static String getRequest(final String key) {
    return WebInspector.get(request, key);
  }

  public static String getOS() {
    return WebInspector.getOS(request);
  }

  public static String getCategory() {
    return WebInspector.getCategory(request);
  }

  public static String getBrowser() {
    return WebInspector.getBrowser(request);
  }

  public static String getVendor() {
    return WebInspector.getVendor(request);
  }

  public static String getVersion() {
    return WebInspector.getVersion(request);
  }

  public static double getVersionNumber() {
    return WebInspector.getVersionNumber(request);
  }

  public static int getMaxInactiveInterval() {
    return request.getSession(true).getMaxInactiveInterval();
  }

  public static int getMaxInactiveIntervalSecond() {
    return Double.valueOf(getMaxInactiveInterval() / 60d).intValue();
  }

  public static DateTime getMaxInactiveIntervalLimit() {
    return DateTime.now().plusMinutes(getMaxInactiveInterval());
  }

  public static boolean isJqueryLegacy() {
    Map<String, String> info = getRequest();
    if (IE_NAME.equals(info.get("name")) && 9d < WebInspector.getVersionNumber(request)) return true;
    if (SAFARI_NAME.equals(info.get("name")) && 5.1 < WebInspector.getVersionNumber(request)) return true;

    return false;
  }

  public static boolean isDevelop() {

    try {
      if (ResourceUtils.containsValue("addr.develop", StringUtils.emptyToSafely(InetAddress.getLocalHost().toString()))) return true;
      if (ResourceUtils.containsValue("addr.develop", StringUtils.emptyToSafely(InetAddress.getLocalHost().getHostName()))) return true;
    } catch (UnknownHostException e) {
      e.printStackTrace();
      return false;
    }

    return ResourceUtils.getProp("param", "staging.port", "nope").equals(Integer.toString(request.getLocalPort()));
  }
}
