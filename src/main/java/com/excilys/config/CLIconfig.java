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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@EnableTransactionManagement
@Configuration
@ComponentScan({ "com.excilys.persistence", "com.excilys.service" })
public class CLIconfig {
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
