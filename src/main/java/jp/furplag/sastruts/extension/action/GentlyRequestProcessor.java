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
/**
 *
 */
package jp.furplag.sastruts.extension.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.furplag.util.Jsonifier;
import jp.furplag.util.commons.StringUtils;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.seasar.framework.beans.util.Beans;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.ReaderUtil;
import org.seasar.struts.action.S2RequestProcessor;
import org.seasar.struts.util.S2ActionMappingUtil;

public class GentlyRequestProcessor extends S2RequestProcessor {

  @Override
  protected void processPopulate(HttpServletRequest request, HttpServletResponse response, ActionForm form, ActionMapping mapping) throws ServletException {
    if (form == null) return;
    if (StringUtils.equalsIgnoreCase("POST", request.getMethod()) && StringUtils.flatten(request.getContentType()).matches("^application/((json)|(javascript)).*$")) {
      form.setServlet(servlet);
      form.setMultipartRequestHandler(null);
      processExecuteConfig(request, response, mapping);
      form.reset(mapping, request);

      Object actionForm = S2ActionMappingUtil.getActionMapping().getActionForm();
      try {
        Beans.copy(Jsonifier.parseLazy(ReaderUtil.readText(request.getReader()), actionForm.getClass()), actionForm).execute();
      } catch (IORuntimeException e) {
        log.error(e.getMessage(), e);
        throw new ServletException(e.getMessage(), e);
      } catch (IOException e) {
        log.error(e.getMessage(), e);
        throw new ServletException(e.getMessage(), e);
      }
    }

    super.processPopulate(request, response, form, mapping);
  }
}
