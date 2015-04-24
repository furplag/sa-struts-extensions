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
package jp.furplag.s2jdbc.extension.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.furplag.sastruts.extension.entity.AbstractEntity;
import jp.furplag.util.commons.StringUtils;

import org.seasar.extension.jdbc.AutoSelect;
import org.seasar.extension.jdbc.SqlFileSelect;
import org.seasar.extension.jdbc.dialect.OracleDialect;
import org.seasar.extension.jdbc.manager.JdbcManagerImplementor;
import org.seasar.extension.jdbc.service.S2AbstractService;

public class AbstractService<T extends AbstractEntity> extends S2AbstractService<T> {

  protected static final String SQL_FILE_SUFFIX = ".sql";

  protected String convertPath(String path) {
    return StringUtils.isSimilarToBlank(path) ? "" : (StringUtils.flatten(path) + SQL_FILE_SUFFIX);
  }

  protected String path(String suffix) {
    return convertPath(Thread.currentThread().getStackTrace()[2].getMethodName() + StringUtils.flatten(suffix));
  }

  protected String path() {
    return convertPath(Thread.currentThread().getStackTrace()[2].getMethodName());
  }

  @Override
  protected void setEntityClass(Class<T> entityClass) {
    super.setEntityClass(entityClass);
    if (StringUtils.isBlank(entityClass.getName())) {
      super.sqlFilePathPrefix = "";
    } else {
      super.sqlFilePathPrefix = entityClass.getName().replaceAll("\\.(entity|dto)\\.", ".sql.").replaceAll("\\.", "/");
      super.sqlFilePathPrefix += "/";
    }
  }

  protected long getSeqCurrentVal(String sequenceName) {
    return getSeqCurrentVal(long.class, sequenceName);
  }

  protected <U> U getSeqCurrentVal(Class<U> valueClass, String sequenceName) {
    String sql = ((JdbcManagerImplementor) jdbcManager).getDialect().getIdentitySelectString(sequenceName, "");
    if (StringUtils.isBlank(sql) && ((JdbcManagerImplementor) jdbcManager).getDialect() instanceof OracleDialect) {
      sql = "select " + sequenceName + ".currval from dual";
    } else {
      sql = sql.replaceAll(sequenceName + "(_)+seq", sequenceName);
    }
    return jdbcManager.selectBySql(valueClass, sql).getSingleResult();
  }

  protected long getSeqNextVal(String sequenceName) {
    return getSeqNextVal(long.class, sequenceName);
  }

  protected <U> U getSeqNextVal(Class<U> valueClass, String sequenceName) {
    return jdbcManager.selectBySql(valueClass, ((JdbcManagerImplementor) jdbcManager).getDialect().getSequenceNextValString(sequenceName, 0)).getSingleResult();
  }

  public int getPageCount(Long rowCount, int pageRowCount) {
    if (!(rowCount != null && rowCount > -1 && pageRowCount != 0)) return 0;
    if (pageRowCount < 0) return rowCount.intValue();
    if (rowCount < 1) return 1;
    if (rowCount <= pageRowCount) return 1;
    return Long.valueOf((rowCount / pageRowCount) + (rowCount % pageRowCount > 0 ? 1 : 0)).intValue();
  }

  public long getPageCount(T entity, int pageRowCount) {
    return pageRowCount == 0 ? 0 : getPageCount(select().where(entity.getWheres()).getCount(), pageRowCount);
  }

  public <U> List<U> finder(Class<U> destClass, Map<String, Object> wheres, String order, Integer limit, Integer offset) {
    return convert(finder(wheres, order, limit, offset), destClass);
  }

  public List<T> finder(Map<String, Object> wheres, String order, Integer limit, Integer offset) {
    AutoSelect<T> select = select();
    if (wheres != null && wheres.size() > 0) select = select.where(wheres);
    if (!StringUtils.isSimilarToBlank(order)) {
      select = select.orderBy(order);
      if (limit != null && offset != null) select = select.limit(limit).offset(offset);
    }
    List<T> ret = select.getResultList();

    return ret == null ? new ArrayList<T>() : ret;
  }

  public List<T> finder(String path, Map<String, Object> wheres, Integer limit, Integer offset) {
    return this.finder(entityClass, path, wheres, limit, offset);
  }

  public <U> List<U> finder(Class<U> destClass, String path, Map<String, Object> wheres, Integer limit, Integer offset) {
    SqlFileSelect<U> select = selectBySqlFile(destClass, path, wheres);
    if (limit != null && offset != null) {
      select = select.limit(limit).offset(offset);
    }
    List<U> ret = select.getResultList();
    return ret == null ? new ArrayList<U>() : ret;
  }

  public <U> List<U> convert(List<T> src, Class<U> destClass) {
    List<U> ret = new ArrayList<U>();
    if (!(src != null && src.size() > 0)) return ret;
    for (T entity : src) ret.add(entity.morph(destClass));

    return ret;
  }
}
