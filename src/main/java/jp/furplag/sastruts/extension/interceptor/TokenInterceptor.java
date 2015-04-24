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

import jp.furplag.sastruts.extension.persistence.Token;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.struts.util.TokenProcessor;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.container.S2Container;

public class TokenInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = -6626634352477491712L;

  private S2Container container;

  public Object invoke(MethodInvocation invocation) throws Throwable {
    if (!invocation.getMethod().isAnnotationPresent(Token.class)) return invocation.proceed();
    HttpServletRequest request = (HttpServletRequest) container.getExternalContext().getRequest();
    TokenProcessor.getInstance().saveToken(request);

    return invocation.proceed();
  }

  public S2Container getContainer() {
    return this.container;
  }

  public void setContainer(S2Container container) {
    this.container = container.getRoot();
  }
}
