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
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.ocpsoft.prettytime.PrettyTime;

public class DateTimeUtils {

  protected DateTimeUtils() {}

  protected static Locale getLocale(final String id) {
    if (StringUtils.isSimilarToBlank(id)) return Locale.ROOT;
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
    if (StringUtils.isSimilarToBlank(id)) return DateTimeZone.UTC;
    try {
      return DateTimeZone.forID(id);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return DateTimeZone.UTC;
  }

  public static String prettify(final Object instant) {
    return prettify(instant, Locale.getDefault(), DateTimeZone.getDefault(), null);
  }

  public static String prettify(final Object instant, final Period limit) {
    return prettify(instant, Locale.getDefault(), DateTimeZone.getDefault(), limit);
  }

  public static String prettify(final Object instant, final String localeId, final String timeZoneId) {
    return prettify(instant, getLocale(localeId), getDateTimeZone(timeZoneId), null);
  }

  public static String prettify(final Object instant, final String localeId, final String timeZoneId, final Period limit) {
    return prettify(instant, getLocale(localeId), getDateTimeZone(timeZoneId), limit);
  }

  public static String prettify(final Object instant, final String localeId, final TimeZone timeZone) {
    return prettify(instant, getLocale(localeId), DateTimeZone.forTimeZone(timeZone), null);
  }

  public static String prettify(final Object instant, final String localeId, final TimeZone timeZone, final Period limit) {
    return prettify(instant, getLocale(localeId), DateTimeZone.forTimeZone(timeZone), limit);
  }

  public static String prettify(final Object instant, final String localeId, final DateTimeZone dateTimeZone) {
    return prettify(instant, getLocale(localeId), dateTimeZone, null);
  }

  public static String prettify(final Object instant, final String localeId, final DateTimeZone dateTimeZone, final Period limit) {
    return prettify(instant, getLocale(localeId), dateTimeZone, limit);
  }

  public static String prettify(final Object instant, final Locale locale, final String timeZoneId) {
    return prettify(instant, locale, getDateTimeZone(timeZoneId), null);
  }

  public static String prettify(final Object instant, final Locale locale, final String timeZoneId, final Period limit) {
    return prettify(instant, locale, getDateTimeZone(timeZoneId), limit);
  }

  public static String prettify(final Object instant, final Locale locale, final TimeZone timeZone) {
    return prettify(instant, locale, DateTimeZone.forTimeZone(timeZone), null);
  }

  public static String prettify(final Object instant, final Locale locale, final TimeZone timeZone, final Period limit) {
    return prettify(instant, locale, DateTimeZone.forTimeZone(timeZone), limit);
  }

  public static String prettify(final Object instant, final Locale locale, final DateTimeZone dateTimeZone) {
    return prettify(instant, locale, dateTimeZone, null);
  }

  public static String prettify(final Object instant, final Locale locale, final DateTimeZone dateTimeZone, final Period limit) {
    if (instant == null) return StringUtils.EMPTY;
    try {
      DateTime temporary = new DateTime(instant, dateTimeZone == null ? DateTimeZone.UTC : dateTimeZone);
      DateTime now = new DateTime(temporary.getZone());
      if (limit == null) return new PrettyTime(now.toDate()).setLocale(locale == null ? Locale.ROOT : locale).formatUnrounded(temporary.toDate());
      if (new Interval(now.minus(limit), now.plus(limit)).contains(temporary)) return new PrettyTime(now.toDate()).setLocale(locale == null ? Locale.ROOT : locale).formatUnrounded(temporary.toDate());
      if (temporary.toString(DateTimeFormat.shortDate()).equals(now.toString(DateTimeFormat.shortDate()))) return temporary.toString(DateTimeFormat.mediumTime());

      return temporary.toString(DateTimeFormat.mediumDateTime());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return StringUtils.EMPTY;
  }

  public static String prettifyWithLimitByDays(final Object instant, final Integer limit) {
    return prettifyWithLimitByDays(instant, Locale.getDefault(), DateTimeZone.getDefault(), limit);
  }

  public static String prettifyWithLimitByDays(final Object instant, final Locale locale, final Integer limit) {
    return prettifyWithLimitByDays(instant, locale, DateTimeZone.getDefault(), limit);
  }

  public static String prettifyWithLimitByDays(final Object instant, final String timeZoneId, final Integer limit) {
    return prettifyWithLimitByDays(instant, Locale.getDefault(), getDateTimeZone(timeZoneId), limit);
  }

  public static String prettifyWithLimitByDays(final Object instant, final TimeZone timeZone, final Integer limit) {
    return prettifyWithLimitByDays(instant, Locale.getDefault(), DateTimeZone.forTimeZone(timeZone), limit);
  }

  public static String prettifyWithLimitByDays(final Object instant, final DateTimeZone dateTimeZone, final Integer limit) {
    return prettifyWithLimitByDays(instant, Locale.getDefault(), dateTimeZone, limit);
  }

  public static String prettifyWithLimitByDays(final Object instant, final String localeId, final String timeZoneId, final Integer limit) {
    return prettifyWithLimitByDays(instant, getLocale(localeId), getDateTimeZone(timeZoneId), limit);
  }

  public static String prettifyWithLimitByDays(final Object instant, final String localeId, final TimeZone timeZone, final Integer limit) {
    return prettifyWithLimitByDays(instant, getLocale(localeId), DateTimeZone.forTimeZone(timeZone), limit);
  }

  public static String prettifyWithLimitByDays(final Object instant, final String localeId, final DateTimeZone dateTimeZone, final Integer limit) {
    return prettifyWithLimitByDays(instant, getLocale(localeId), dateTimeZone, limit);
  }

  public static String prettifyWithLimitByDays(final Object instant, final Locale locale, final String timeZoneId, final Integer limit) {
    return prettifyWithLimitByDays(instant, locale, getDateTimeZone(timeZoneId), limit);
  }

  public static String prettifyWithLimitByDays(final Object instant, final Locale locale, final TimeZone timeZone, final Integer limit) {
    return prettifyWithLimitByDays(instant, locale, DateTimeZone.forTimeZone(timeZone), limit);
  }

  public static String prettifyWithLimitByDays(final Object instant, final Locale locale, final DateTimeZone dateTimeZone, final Integer limit) {
    return prettify(instant, locale, dateTimeZone, limit == null ? null : new Period().withDays(limit * (limit < 0 ? -1 : 1) + 1));
  }

  public static String prettifyWithLimitByHours(final Object instant, final Integer limit) {
    return prettifyWithLimitByHours(instant, Locale.getDefault(), DateTimeZone.getDefault(), limit);
  }

  public static String prettifyWithLimitByHours(final Object instant, final Locale locale, final Integer limit) {
    return prettifyWithLimitByHours(instant, locale, DateTimeZone.getDefault(), limit);
  }

  public static String prettifyWithLimitByHours(final Object instant, final String timeZoneId, final Integer limit) {
    return prettifyWithLimitByHours(instant, Locale.getDefault(), getDateTimeZone(timeZoneId), limit);
  }

  public static String prettifyWithLimitByHours(final Object instant, final TimeZone timeZone, final Integer limit) {
    return prettifyWithLimitByHours(instant, Locale.getDefault(), DateTimeZone.forTimeZone(timeZone), limit);
  }

  public static String prettifyWithLimitByHours(final Object instant, final DateTimeZone dateTimeZone, final Integer limit) {
    return prettifyWithLimitByHours(instant, Locale.getDefault(), dateTimeZone, limit);
  }

  public static String prettifyWithLimitByHours(final Object instant, final String localeId, final String timeZoneId, final Integer limit) {
    return prettifyWithLimitByHours(instant, getLocale(localeId), getDateTimeZone(timeZoneId), limit);
  }

  public static String prettifyWithLimitByHours(final Object instant, final String localeId, final TimeZone timeZone, final Integer limit) {
    return prettifyWithLimitByHours(instant, getLocale(localeId), DateTimeZone.forTimeZone(timeZone), limit);
  }

  public static String prettifyWithLimitByHours(final Object instant, final String localeId, final DateTimeZone dateTimeZone, final Integer limit) {
    return prettifyWithLimitByHours(instant, getLocale(localeId), dateTimeZone, limit);
  }

  public static String prettifyWithLimitByHours(final Object instant, final Locale locale, final String timeZoneId, final Integer limit) {
    return prettifyWithLimitByHours(instant, locale, getDateTimeZone(timeZoneId), limit);
  }

  public static String prettifyWithLimitByHours(final Object instant, final Locale locale, final TimeZone timeZone, final Integer limit) {
    return prettifyWithLimitByHours(instant, locale, DateTimeZone.forTimeZone(timeZone), limit);
  }

  public static String prettifyWithLimitByHours(final Object instant, final Locale locale, final DateTimeZone dateTimeZone, final Integer limit) {
    return prettify(instant, locale, dateTimeZone, limit == null ? null : new Period().withHours(limit * (limit < 0 ? -1 : 1) + 1));
  }

  public static String prettifyWithLimitByMonths(final Object instant, final Integer limit) {
    return prettifyWithLimitByMonths(instant, Locale.getDefault(), DateTimeZone.getDefault(), limit);
  }

  public static String prettifyWithLimitByMonths(final Object instant, final Locale locale, final Integer limit) {
    return prettifyWithLimitByMonths(instant, locale, DateTimeZone.getDefault(), limit);
  }

  public static String prettifyWithLimitByMonths(final Object instant, final String timeZoneId, final Integer limit) {
    return prettifyWithLimitByMonths(instant, Locale.getDefault(), getDateTimeZone(timeZoneId), limit);
  }

  public static String prettifyWithLimitByMonths(final Object instant, final TimeZone timeZone, final Integer limit) {
    return prettifyWithLimitByMonths(instant, Locale.getDefault(), DateTimeZone.forTimeZone(timeZone), limit);
  }

  public static String prettifyWithLimitByMonths(final Object instant, final DateTimeZone dateTimeZone, final Integer limit) {
    return prettifyWithLimitByMonths(instant, Locale.getDefault(), dateTimeZone, limit);
  }

  public static String prettifyWithLimitByMonths(final Object instant, final String localeId, final String timeZoneId, final Integer limit) {
    return prettifyWithLimitByMonths(instant, getLocale(localeId), getDateTimeZone(timeZoneId), limit);
  }

  public static String prettifyWithLimitByMonths(final Object instant, final String localeId, final TimeZone timeZone, final Integer limit) {
    return prettifyWithLimitByMonths(instant, getLocale(localeId), DateTimeZone.forTimeZone(timeZone), limit);
  }

  public static String prettifyWithLimitByMonths(final Object instant, final String localeId, final DateTimeZone dateTimeZone, final Integer limit) {
    return prettifyWithLimitByMonths(instant, getLocale(localeId), dateTimeZone, limit);
  }

  public static String prettifyWithLimitByMonths(final Object instant, final Locale locale, final String timeZoneId, final Integer limit) {
    return prettifyWithLimitByMonths(instant, locale, getDateTimeZone(timeZoneId), limit);
  }

  public static String prettifyWithLimitByMonths(final Object instant, final Locale locale, final TimeZone timeZone, final Integer limit) {
    return prettifyWithLimitByMonths(instant, locale, DateTimeZone.forTimeZone(timeZone), limit);
  }

  public static String prettifyWithLimitByMonths(final Object instant, final Locale locale, final DateTimeZone dateTimeZone, final Integer limit) {
    return prettify(instant, locale, dateTimeZone, limit == null ? null : new Period().withMonths(limit * (limit < 0 ? -1 : 1) + 1));
  }

  public static String prettifyWithLimitByWeeks(final Object instant, final Integer limit) {
    return prettifyWithLimitByWeeks(instant, Locale.getDefault(), DateTimeZone.getDefault(), limit);
  }

  public static String prettifyWithLimitByWeeks(final Object instant, final Locale locale, final Integer limit) {
    return prettifyWithLimitByWeeks(instant, locale, DateTimeZone.getDefault(), limit);
  }

  public static String prettifyWithLimitByWeeks(final Object instant, final String timeZoneId, final Integer limit) {
    return prettifyWithLimitByWeeks(instant, Locale.getDefault(), getDateTimeZone(timeZoneId), limit);
  }

  public static String prettifyWithLimitByWeeks(final Object instant, final TimeZone timeZone, final Integer limit) {
    return prettifyWithLimitByWeeks(instant, Locale.getDefault(), DateTimeZone.forTimeZone(timeZone), limit);
  }

  public static String prettifyWithLimitByWeeks(final Object instant, final DateTimeZone dateTimeZone, final Integer limit) {
    return prettifyWithLimitByWeeks(instant, Locale.getDefault(), dateTimeZone, limit);
  }

  public static String prettifyWithLimitByWeeks(final Object instant, final String localeId, final String timeZoneId, final Integer limit) {
    return prettifyWithLimitByWeeks(instant, getLocale(localeId), getDateTimeZone(timeZoneId), limit);
  }

  public static String prettifyWithLimitByWeeks(final Object instant, final String localeId, final TimeZone timeZone, final Integer limit) {
    return prettifyWithLimitByWeeks(instant, getLocale(localeId), DateTimeZone.forTimeZone(timeZone), limit);
  }

  public static String prettifyWithLimitByWeeks(final Object instant, final String localeId, final DateTimeZone dateTimeZone, final Integer limit) {
    return prettifyWithLimitByWeeks(instant, getLocale(localeId), dateTimeZone, limit);
  }

  public static String prettifyWithLimitByWeeks(final Object instant, final Locale locale, final String timeZoneId, final Integer limit) {
    return prettifyWithLimitByWeeks(instant, locale, getDateTimeZone(timeZoneId), limit);
  }

  public static String prettifyWithLimitByWeeks(final Object instant, final Locale locale, final TimeZone timeZone, final Integer limit) {
    return prettifyWithLimitByWeeks(instant, locale, DateTimeZone.forTimeZone(timeZone), limit);
  }

  public static String prettifyWithLimitByWeeks(final Object instant, final Locale locale, final DateTimeZone dateTimeZone, final Integer limit) {
    return prettify(instant, locale, dateTimeZone, limit == null ? null : new Period().withWeeks(limit * (limit < 0 ? -1 : 1) + 1));
  }

  public static String prettifyWithLimitByYears(final Object instant, final Integer limit) {
    return prettifyWithLimitByYears(instant, Locale.getDefault(), DateTimeZone.getDefault(), limit);
  }

  public static String prettifyWithLimitByYears(final Object instant, final Locale locale, final Integer limit) {
    return prettifyWithLimitByYears(instant, locale, DateTimeZone.getDefault(), limit);
  }

  public static String prettifyWithLimitByYears(final Object instant, final String timeZoneId, final Integer limit) {
    return prettifyWithLimitByYears(instant, Locale.getDefault(), getDateTimeZone(timeZoneId), limit);
  }

  public static String prettifyWithLimitByYears(final Object instant, final TimeZone timeZone, final Integer limit) {
    return prettifyWithLimitByYears(instant, Locale.getDefault(), DateTimeZone.forTimeZone(timeZone), limit);
  }

  public static String prettifyWithLimitByYears(final Object instant, final DateTimeZone dateTimeZone, final Integer limit) {
    return prettifyWithLimitByYears(instant, Locale.getDefault(), dateTimeZone, limit);
  }

  public static String prettifyWithLimitByYears(final Object instant, final String localeId, final String timeZoneId, final Integer limit) {
    return prettifyWithLimitByYears(instant, getLocale(localeId), getDateTimeZone(timeZoneId), limit);
  }

  public static String prettifyWithLimitByYears(final Object instant, final String localeId, final TimeZone timeZone, final Integer limit) {
    return prettifyWithLimitByYears(instant, getLocale(localeId), DateTimeZone.forTimeZone(timeZone), limit);
  }

  public static String prettifyWithLimitByYears(final Object instant, final String localeId, final DateTimeZone dateTimeZone, final Integer limit) {
    return prettifyWithLimitByYears(instant, getLocale(localeId), dateTimeZone, limit);
  }

  public static String prettifyWithLimitByYears(final Object instant, final Locale locale, final String timeZoneId, final Integer limit) {
    return prettifyWithLimitByYears(instant, locale, getDateTimeZone(timeZoneId), limit);
  }

  public static String prettifyWithLimitByYears(final Object instant, final Locale locale, final TimeZone timeZone, final Integer limit) {
    return prettifyWithLimitByYears(instant, locale, DateTimeZone.forTimeZone(timeZone), limit);
  }

  public static String prettifyWithLimitByYears(final Object instant, final Locale locale, final DateTimeZone dateTimeZone, final Integer limit) {
    return prettify(instant, locale, dateTimeZone, limit == null ? null : new Period().withYears(limit * (limit < 0 ? -1 : 1) + 1));
  }

  public static String prettifyWithLocale(final Object instant, final String localeId) {
    return prettify(instant, getLocale(localeId), DateTimeZone.getDefault(), null);
  }

  public static String prettifyWithLocale(final Object instant, final Locale locale) {
    return prettify(instant, locale, DateTimeZone.getDefault(), null);
  }

  public static String prettifyZoned(final Object instant, final String timeZoneId) {
    return prettify(instant, Locale.getDefault(), getDateTimeZone(timeZoneId), null);
  }

  public static String prettifyZoned(final Object instant, final TimeZone timeZone) {
    return prettify(instant, Locale.getDefault(), DateTimeZone.forTimeZone(timeZone), null);
  }

  public static String prettifyZoned(final Object instant, final DateTimeZone dateTimeZone) {
    return prettify(instant, Locale.getDefault(), dateTimeZone, null);
  }
}
