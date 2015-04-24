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

import jp.furplag.sastruts.extension.persistence.LinearResponse;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.framework.aop.interceptors.ThrowsInterceptor;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.util.ActionMessagesUtil;
import org.seasar.struts.util.RequestUtil;

public class ThrowableInterceptor extends ThrowsInterceptor {

  private static final long serialVersionUID = 4357661430968420869L;

  public ThrowableInterceptor() {}

  public Object handleThrowable(Throwable throwable, MethodInvocation invocation) throws Throwable {
    final Log log = LogFactory.getLog(getTargetClass(invocation));
    Method method = invocation.getMethod();
    log.error(getTargetClass(invocation).getSimpleName() + "#" + method.getName(), throwable);
    if (method.isAnnotationPresent(Execute.class)) {
      ActionMessages messages = new ActionMessages();
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.throwable"));
      messages.add("errors.detail", new ActionMessage(throwable.getClass().getName(), false));
      StackTraceElement[] st = throwable.getStackTrace();
      for (int i = 0; i < (st.length < 5 ? st.length : 5); i++) {
        messages.add("errors.detail", new ActionMessage(st[i].toString(), false));
      }
      if (st.length > 5) messages.add("errors.detail", new ActionMessage("and more...", false));
      ActionMessagesUtil.addErrors(RequestUtil.getRequest().getSession(true), messages);

      return method.isAnnotationPresent(LinearResponse.class) ? "/fatal/linear?redirect=true" : "/fatal/?redirect=true";
    }
    log.error(throwable.getClass().getName());
    StackTraceElement[] st = throwable.getStackTrace();
    for (int i = 0; i < (st.length < 5 ? st.length : 5); i++) {
      log.error(st[i].toString());
    }
    if (st.length > 5) log.error("and more...");

    throw throwable;
  }
}
