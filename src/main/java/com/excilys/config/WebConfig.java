package com.excilys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@EnableWebMvc
@EnableTransactionManagement
@Configuration
@ComponentScan(basePackages = { "com.excilys.controllers" })
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();

		bean.setViewClass(JstlView.class);
		bean.setPrefix("/static/views/");
		bean.setSuffix(".jsp");

		return bean;
	}

	@Bean
	public HikariDataSource mysqlDataSource() {
		HikariConfig config = new HikariConfig("/hikari.properties");
		return new HikariDataSource(config);
	}

	@Bean
	public NamedParameterJdbcTemplate namedJdbcTemplate() {
		return new NamedParameterJdbcTemplate(mysqlDataSource());
	}
}