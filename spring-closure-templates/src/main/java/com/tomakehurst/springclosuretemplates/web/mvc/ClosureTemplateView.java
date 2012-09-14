package com.tomakehurst.springclosuretemplates.web.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tomakehurst.springclosuretemplates.web.mvc.inject.CommonInjectedDataProvider;
import org.springframework.web.servlet.view.AbstractTemplateView;

import com.google.template.soy.tofu.SoyTofu;

public class ClosureTemplateView extends AbstractTemplateView {
	
	private SoyTofu compiledTemplates;
	private String templateName;
    private CommonInjectedDataProvider commonInjectedDataProvider;

    @Override
	protected void renderMergedTemplateModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
        SoyTofu.Renderer renderer = compiledTemplates.newRenderer(templateName).setData(model);

        if (commonInjectedDataProvider != null) {
            renderer.setIjData(commonInjectedDataProvider.get(model, request, response));
        }

		response.getWriter().write(renderer.render());
	}
	
	public void setCompiledTemplates(SoyTofu compiledTemplates) {
		this.compiledTemplates = compiledTemplates;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

    public void setCommonInjectedDataProvider(CommonInjectedDataProvider commonInjectedDataProvider) {
        this.commonInjectedDataProvider = commonInjectedDataProvider;
    }
}
