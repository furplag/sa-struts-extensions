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

import jp.furplag.util.WebInspector;
import jp.furplag.util.commons.StringUtils;

import org.joda.time.DateTime;
import org.seasar.struts.util.RequestUtil;

public class WebclientUtils {

  protected static final String IE_NAME = "internetexplorer";

  protected static final String SAFARI_NAME = "safari";

  protected WebclientUtils() {}

  public static Map<String, String> getRequest() {
    return WebInspector.get(RequestUtil.getRequest());
  }

  public static String getRequest(final String key) {
    return WebInspector.get(RequestUtil.getRequest(), key);
  }

  public static String getOS() {
    return WebInspector.getOS(RequestUtil.getRequest());
  }

  public static String getCategory() {
    return WebInspector.getCategory(RequestUtil.getRequest());
  }

  public static String getBrowser() {
    return WebInspector.getBrowser(RequestUtil.getRequest());
  }

  public static String getVendor() {
    return WebInspector.getVendor(RequestUtil.getRequest());
  }

  public static String getVersion() {
    return WebInspector.getVersion(RequestUtil.getRequest());
  }

  public static double getVersionNumber() {
    return WebInspector.getVersionNumber(RequestUtil.getRequest());
  }

  public static int getMaxInactiveInterval() {
    return RequestUtil.getRequest().getSession(true).getMaxInactiveInterval();
  }

  public static int getMaxInactiveIntervalSecond() {
    return Double.valueOf(getMaxInactiveInterval() * 60d).intValue();
  }

  public static int getMaxInactiveIntervalMilliSecond() {
    return Double.valueOf(getMaxInactiveInterval() * 60d * 1000d).intValue();
  }

  public static DateTime getMaxInactiveIntervalLimit() {
    return DateTime.now().plusMinutes(getMaxInactiveInterval());
  }

  public static boolean isJqueryLegacy() {
    return WebInspector.isJqueryLegacy(RequestUtil.getRequest());
  }

  public static boolean isDevelop() {
    try {
      InetAddress in = InetAddress.getLocalHost();
      if (ResourceUtils.containsValue("addr.develop", StringUtils.emptyToSafely(in.getHostAddress()))) return true;
      if (ResourceUtils.containsValue("addr.develop", StringUtils.emptyToSafely(in.getHostName()))) return true;
      if (ResourceUtils.containsValue("addr.develop", RequestUtil.getRequest().getRequestURL().toString().replaceAll("^http(s)?:\\/\\/", "").replaceAll("\\/.*$", "").replaceAll(":.*$", ""))) return true;
      if (ResourceUtils.getProp("param", "staging.port", "nope").equals(Integer.toString(RequestUtil.getRequest().getLocalPort()))) return true;
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (NullPointerException e) {
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }
}
