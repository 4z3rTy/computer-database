package com.excilys.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
@Import(PersistenceConfig.class)
@ComponentScan({ "com.excilys.persistence.ComputerDAO","com.excilys.persistence.CompanyDAO", "com.excilys.service.ComputerService", "com.excilys.service.CompanyService" })
public class CLIconfig {

}
