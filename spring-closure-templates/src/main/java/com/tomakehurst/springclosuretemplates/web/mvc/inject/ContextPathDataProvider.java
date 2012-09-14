package com.tomakehurst.springclosuretemplates.web.mvc.inject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

public class ContextPathDataProvider implements CommonInjectedDataProvider {

    public static final String CONTEXT_PATH = "contextPath";

    private Map<String, Object> contextPathMap = null;

    @Override
    public synchronized Map<String, Object> get(Map<String, Object> model,
                                                HttpServletRequest request,
                                                HttpServletResponse response) {
        if (contextPathMap == null) {
            contextPathMap =
                    Collections.synchronizedMap(
                            Collections.singletonMap(CONTEXT_PATH, (Object) request.getContextPath()));
        }

        return contextPathMap;
    }
}
