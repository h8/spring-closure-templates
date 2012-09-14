package com.tomakehurst.springclosuretemplates.web.mvc.inject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface CommonInjectedDataProvider {
    public Map<String, Object> get(Map<String, Object> model,
                                   HttpServletRequest request,
                                   HttpServletResponse response);
}
