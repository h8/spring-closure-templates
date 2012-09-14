package com.tomakehurst.springclosuretemplates.web.mvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.tomakehurst.springclosuretemplates.web.mvc.inject.CommonInjectedDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.google.template.soy.SoyFileSet;
import com.google.template.soy.tofu.SoyTofu;
import com.google.template.soy.tofu.SoyTofuException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClosureTemplateViewTest {
	
	private SoyFileSet sfs;
	private ClosureTemplateView view;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private Map<String, Object> model;
	
	@Before
	public void init() throws Exception {
		view = new ClosureTemplateView();
		sfs = (new SoyFileSet.Builder())
                .add(new ClassPathResource("test-closure-templates/example-one.soy").getFile())
                .add(new ClassPathResource("test-closure-templates/example-three.soy").getFile())
                .build();
		SoyTofu compiledTemplates = sfs.compileToJavaObj();
		view.setCompiledTemplates(compiledTemplates);
		
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		model = new HashMap<String, Object>();
	}

	@Test
	public void shouldRenderView() throws Exception {
		view.setTemplateName("com.tomakehurst.helloName");
		
		model.put("name", "Tom");
		view.renderMergedTemplateModel(model, request, response);
		
		String content = response.getContentAsString();
		assertThat(content, containsString("Hello Tom"));
	}
	
	@Test(expected=SoyTofuException.class)
	public void shouldThrowExceptionWhenTemplateNameNotValid() throws Exception {
		view.setTemplateName("com.tomakehurst.somethingwrong");
		
		view.renderMergedTemplateModel(model, request, response);
	}

    @Test
    public void shouldRenderInjectedData() throws Exception {
        view.setCommonInjectedDataProvider(new CommonInjectedDataProvider() {
            @Override
            public Map<String, Object> get(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
                return Collections.singletonMap("foo", (Object) "bar");
            }
        });

        view.setTemplateName("com.tomakehurst.ijData");
        view.renderMergedTemplateModel(model, request, response);

        String content = response.getContentAsString();
        assertThat(content, containsString("foo is bar"));
    }
}
