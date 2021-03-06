package com.excilys.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class CDBInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(final ServletContext sc) throws ServletException {

		AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
		root.register(WebConfig.class, WebSecurityConfig.class);

		ServletRegistration.Dynamic appServlet = sc.addServlet("mvc", new DispatcherServlet(root));
		appServlet.setLoadOnStartup(1);
		appServlet.addMapping("/");
	}
}
