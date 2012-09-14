package com.tomakehurst.springclosuretemplates.web.mvc.inject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChainDataProvider implements CommonInjectedDataProvider {

    private List<CommonInjectedDataProvider> providers;

    public void setProviders(List<CommonInjectedDataProvider> providers) {
        this.providers = providers;
    }

    @Override
    public Map<String, Object> get(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();

        for (CommonInjectedDataProvider provider : providers) {
            result.putAll(provider.get(model, request, response));
        }

        return result;
    }
}
