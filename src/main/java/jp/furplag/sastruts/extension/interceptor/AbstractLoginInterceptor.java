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
package jp.furplag.sastruts.extension.interceptor;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import jp.furplag.sastruts.extension.dto.OriginuserDto;
import jp.furplag.sastruts.extension.persistence.LinearResponse;
import jp.furplag.sastruts.extension.persistence.Logging;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.util.ActionMessagesUtil;
import org.seasar.struts.util.RequestUtil;

import statics.jp.furplag.sastruts.extension.util.ResourceUtils;

public abstract class AbstractLoginInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = 3361525807255347648L;

  @Resource
  protected OriginuserDto originuserDto;

  public AbstractLoginInterceptor() {}

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {

    Method method = invocation.getMethod();
    if (method.isAnnotationPresent(Execute.class) && !method.isAnnotationPresent(jp.furplag.sastruts.extension.persistence.Anonymous.class)) {
      ActionMessages messages = new ActionMessages();
      if (originuserDto == null) {
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ResourceUtils.getProp("errors.timeout"), false));
      } else if (!originuserDto.isAuthenticated()) {
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ResourceUtils.getProp("errors.auth"), false));
      }
      if (messages.size() < 1 && method.isAnnotationPresent(jp.furplag.sastruts.extension.persistence.Admin.class) && !originuserDto.isAdministrable()) messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.permission.admin"));
      if (messages.size() < 1 && method.isAnnotationPresent(jp.furplag.sastruts.extension.persistence.Config.class) && !originuserDto.isConfigurable()) messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.permission.config"));
      if (messages.size() > 0) {
        ActionMessagesUtil.addErrors(RequestUtil.getRequest().getSession(), messages);
        return method.isAnnotationPresent(LinearResponse.class) ? "/fatal/linear?redirect=true" : "/fatal/?redirect=true";
      }
    }
    final Log log = LogFactory.getLog(getTargetClass(invocation));
    final long start = System.currentTimeMillis();
    if (method.isAnnotationPresent(Execute.class)) log.info("START " + getTargetClass(invocation).getSimpleName() + "#" + invocation.getMethod().getName());
    Object ret = invocation.proceed();
    if (method.isAnnotationPresent(Execute.class)) log.info("END " + getTargetClass(invocation).getSimpleName() + "#" + invocation.getMethod().getName()+ " - " + (System.currentTimeMillis() - start) + "msec.");
    if (method.isAnnotationPresent(Logging.class)) logging(method);

    return ret;
  }

  abstract public void logging(Method method);
}
