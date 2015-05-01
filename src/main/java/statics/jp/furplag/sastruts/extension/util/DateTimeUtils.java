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

import java.util.Locale;
import java.util.TimeZone;

import jp.furplag.util.commons.StringUtils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.ocpsoft.prettytime.PrettyTime;

public class DateTimeUtils {

  protected DateTimeUtils() {}

  protected static Locale getLocale(final String id) {
    if (!StringUtils.isSimilarToBlank(id)) return Locale.ROOT;
    try {
      if (!StringUtils.contains(id, "_")) return new Locale(id);
      String[] ids = StringUtils.split(id, "_");
      if (ids.length > 2) return new Locale(ids[0], ids[1], ids[2]);
      if (ids.length > 1) return new Locale(ids[0], ids[1]);

      return new Locale(ids[0]);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return Locale.ROOT;
  }

  protected static DateTimeZone getDateTimeZone(final String id) {
    if (!StringUtils.isSimilarToBlank(id)) return DateTimeZone.UTC;
    try {
      return DateTimeZone.forID(id);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return DateTimeZone.UTC;
  }

  public static String prettify(final Object instant) {
    return prettify(instant, Locale.getDefault(), DateTimeZone.getDefault());
  }

  public static String prettify(final Object instant, final String localeId, final String timeZoneId) {
    return prettify(instant, getLocale(localeId), getDateTimeZone(timeZoneId));
  }

  public static String prettify(final Object instant, final String localeId, final TimeZone timeZone) {
    return prettify(instant, getLocale(localeId), DateTimeZone.forTimeZone(timeZone));
  }

  public static String prettify(final Object instant, final String localeId, final DateTimeZone dateTimeZone) {
    return prettify(instant, getLocale(localeId), dateTimeZone);
  }

  public static String prettify(final Object instant, final Locale locale, final String timeZoneId) {
    return prettify(instant, locale, getDateTimeZone(timeZoneId));
  }

  public static String prettify(final Object instant, final Locale locale, final TimeZone timeZone) {
    return prettify(instant, locale, DateTimeZone.forTimeZone(timeZone));
  }

  public static String prettify(final Object instant, final Locale locale, final DateTimeZone dateTimeZone) {
    if (instant == null) return StringUtils.EMPTY;
    try {
      return new PrettyTime(new DateTime(dateTimeZone == null ? DateTimeZone.UTC : dateTimeZone).toDate()).setLocale(locale == null ? Locale.ROOT : locale).format(new DateTime(instant, dateTimeZone).toDate());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return StringUtils.EMPTY;
  }

  public static String prettifyWithLocale(final Object instant, final String localeId) {
    return prettify(instant, getLocale(localeId), DateTimeZone.getDefault());
  }

  public static String prettifyWithLocale(final Object instant, final Locale locale) {
    return prettify(instant, locale, DateTimeZone.getDefault());
  }

  public static String prettifyZoned(final Object instant, final String timeZoneId) {
    return prettify(instant, Locale.getDefault(), getDateTimeZone(timeZoneId));
  }

  public static String prettifyZoned(final Object instant, final TimeZone timeZone) {
    return prettify(instant, Locale.getDefault(), DateTimeZone.forTimeZone(timeZone));
  }

  public static String prettifyZoned(final Object instant, final DateTimeZone dateTimeZone) {
    return prettify(instant, Locale.getDefault(), dateTimeZone);
  }
}
