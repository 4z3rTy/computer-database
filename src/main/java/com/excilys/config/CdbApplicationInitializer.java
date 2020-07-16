package com.excilys.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({"com.excilys.service","com.excilys.persistence"})
@PropertySource("classpath:local.properties")
public class CdbApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container) {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(WebConfig.class);

		container.addListener(new ContextLoaderListener(context));

		ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(context));

		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/computerdatabase");
	}
	
	@Bean
	  public HikariDataSource mysqlDataSource() {
		  HikariConfig config = new HikariConfig("/hikari.properties");
	      return new HikariDataSource(config);
	  }

}
