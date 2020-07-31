package com.excilys.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EnableWebMvc
@EnableTransactionManagement
@Configuration
@Import(PersistenceConfig.class)
@ComponentScan(basePackages = {"com.excilys.service", "com.excilys.controllers" })
public class WebConfig implements WebMvcConfigurer {

}