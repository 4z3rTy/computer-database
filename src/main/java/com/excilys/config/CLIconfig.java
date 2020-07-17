package com.excilys.config;

import org.springframework.context.annotation.Bean;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class CLIconfig {
	@Bean
	  public HikariDataSource mysqlDataSource() {
		  HikariConfig config = new HikariConfig("/hikari.properties");
	      return new HikariDataSource(config);
	  }
}
