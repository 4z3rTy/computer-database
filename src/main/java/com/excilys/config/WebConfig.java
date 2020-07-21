package com.excilys.config;

import javax.persistence.EntityManagerFactory;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.DefaultJpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
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
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager(getEntityManagerFactory());

	}
	

	@Bean
	public EntityManagerFactory getEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lcefb = new LocalContainerEntityManagerFactoryBean();

		lcefb.setDataSource(mysqlDataSource());
		lcefb.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		lcefb.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		lcefb.setPackagesToScan("com.excilys.model");
		lcefb.setJpaDialect(new DefaultJpaDialect());
		lcefb.afterPropertiesSet();
		return lcefb.getNativeEntityManagerFactory();
	}
}