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
package jp.furplag.struts.initializer.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import jp.furplag.util.commons.StringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;
import org.seasar.framework.container.ComponentNotFoundRuntimeException;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.exception.SQLRuntimeException;

public class InitializeServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  private static final Log LOG = LogFactory.getLog(InitializeServlet.class);

  protected static final String ACTIVATION_PARAM = "activate";

  protected static final String BUNDLE = "initializer";

  @Override
  public void init() throws ServletException {
    MessageResources mr = MessageResources.getMessageResources(BUNDLE);
    LOG.info(mr.getMessage("start"));
    final long start = System.currentTimeMillis();
    jp.furplag.struts.initializer.dto.PropsDto propsDto = null;
    try {
      propsDto = SingletonS2Container.getComponent(jp.furplag.struts.initializer.dto.PropsDto.class);
      if (StringUtils.flatten(getInitParameter(ACTIVATION_PARAM)).matches("^t(rue)?|y(es)?|on|1$")) {
        propsDto.setProps(SingletonS2Container.getComponent(jp.furplag.struts.initializer.service.PropDtoService.class).getProps());
      } else {
        LOG.info(mr.getMessage("skip retrieving of external properties."));
      }
    } catch (ComponentNotFoundRuntimeException e) {
      e.printStackTrace();
      LOG.fatal((propsDto == null ? "PropsDto" : "PropDtoService") + " not found.");
      LOG.fatal("check your [src/resources/app.dicon] and configure.");
    } catch (SQLRuntimeException e) {
      e.printStackTrace();
      LOG.fatal("check your [src/resources/customizer.dicon, src/resources/jdbc.dicon, src/resources/s2jdbc.dicon] and configure.");
      LOG.fatal("check your dependent-library([src/main/webapp/WEB-INF/lib]).");
    } catch (Exception e) {
      e.printStackTrace();
      LOG.fatal("check your [src/main/webapp/WEB-INF/web.xml, src/resources/*.dicon] config, and see below.");
      for (StackTraceElement s : e.getStackTrace()) {
        LOG.fatal(s.toString());
      }
    }
    LOG.info(mr.getMessage(propsDto == null ? "fatal" : "result", propsDto == null ? new Object[]{} : new Object[]{"", propsDto.getCount(), ""}));
    LOG.info(mr.getMessage("end", new Object[]{System.currentTimeMillis() - start}));
  }
}
