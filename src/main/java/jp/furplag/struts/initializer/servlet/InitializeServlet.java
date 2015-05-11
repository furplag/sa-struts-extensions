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
package jp.furplag.struts.initializer.servlet;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import jp.furplag.util.commons.StringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.seasar.framework.container.ComponentNotFoundRuntimeException;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.exception.SQLRuntimeException;

public class InitializeServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  private static final Log LOG = LogFactory.getLog(InitializeServlet.class);

  protected static final String ACTIVATION_PARAM = "activate";

  protected static final String BUNDLE = "jp.furplag.struts.initializer.i18n.Resources";

  @Override
  public void init() throws ServletException {
    ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE);
    LOG.info(bundle.getString("start"));
    final DateTime start = DateTime.now();
    jp.furplag.struts.initializer.dto.PropsDto propsDto = null;
    try {
      propsDto = SingletonS2Container.getComponent(jp.furplag.struts.initializer.dto.PropsDto.class);
      if (StringUtils.flatten(getInitParameter(ACTIVATION_PARAM)).matches("^t(rue)?|y(es)?|on|1$")) {
        propsDto.setProps(SingletonS2Container.getComponent(jp.furplag.struts.initializer.service.PropDtoService.class).getProps());
      } else {
        LOG.info(bundle.getString("inactive"));
      }
    } catch (ComponentNotFoundRuntimeException e) {
      e.printStackTrace();
      LOG.fatal(MessageFormat.format(bundle.getString("fatal.component"), propsDto == null ? "PropsDto" : "PropDtoService"));
    } catch (SQLRuntimeException e) {
      e.printStackTrace();
      LOG.fatal(bundle.getString("fatal.sql"));
    } catch (Exception e) {
      e.printStackTrace();
      LOG.fatal(bundle.getString("fatal.any"));
      for (StackTraceElement s : e.getStackTrace()) {
        LOG.fatal(s.toString());
      }
    }
    LOG.info(propsDto == null ? bundle.getString("fatal") : MessageFormat.format(bundle.getString("result"), new Object[]{"", propsDto.getCount(), ""}));
    LOG.info(MessageFormat.format(bundle.getString("end"), new Object[]{PeriodFormat.wordBased().print(new Period(start, DateTime.now()))}));
  }
}
