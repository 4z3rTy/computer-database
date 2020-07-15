package com.excilys.config;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({"com.excilys.service","com.excilys.persistence"})
@PropertySource("classpath:local.properties")

public class SpringAnnotationWebInitializer extends AbstractContextLoaderInitializer {
	
  @Override
  protected WebApplicationContext createRootApplicationContext() {
    AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
    applicationContext.register(SpringAnnotationWebInitializer.class);
    return applicationContext;
  }
  
  @Bean
  public HikariDataSource mysqlDataSource() {
	  HikariConfig config = new HikariConfig("/hikari.properties");
      return new HikariDataSource(config);
  }
}