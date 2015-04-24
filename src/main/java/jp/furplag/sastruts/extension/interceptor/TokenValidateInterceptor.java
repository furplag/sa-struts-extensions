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

import javax.servlet.http.HttpServletRequest;

import jp.furplag.sastruts.extension.persistence.LinearResponse;
import jp.furplag.sastruts.extension.persistence.TokenValidate;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.TokenProcessor;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.container.S2Container;
import org.seasar.struts.util.ActionMessagesUtil;
import org.seasar.struts.util.RequestUtil;

public class TokenValidateInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = -2629233953919243195L;

  private S2Container container;

  public Object invoke(MethodInvocation invocation) throws Throwable {
    if (!invocation.getMethod().isAnnotationPresent(TokenValidate.class)) return invocation.proceed();
    if (!TokenProcessor.getInstance().isTokenValid((HttpServletRequest) container.getExternalContext().getRequest(), true)) {
      ActionMessages messages = new ActionMessages();
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.token"));
      ActionMessagesUtil.addErrors(RequestUtil.getRequest().getSession(true), messages);
      return invocation.getMethod().isAnnotationPresent(LinearResponse.class) ? "/fatal/linear?redirect=true" : "/fatal/?redirect=true";
    }

    return invocation.proceed();
  }

  public S2Container getContainer() {
    return this.container;
  }

  public void setContainer(S2Container container) {
    this.container = container.getRoot();
  }
}
