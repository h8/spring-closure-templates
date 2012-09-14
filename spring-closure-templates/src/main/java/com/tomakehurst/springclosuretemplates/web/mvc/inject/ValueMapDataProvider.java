package com.tomakehurst.springclosuretemplates.web.mvc.inject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ValueMapDataProvider implements CommonInjectedDataProvider {

    private Map<String, Object> values = null;

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }

    @Override
    public Map<String, Object> get(Map<String, Object> model,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        return values;
    }
}
