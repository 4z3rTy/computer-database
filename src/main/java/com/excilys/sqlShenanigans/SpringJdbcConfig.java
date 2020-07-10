package com.excilys.sqlShenanigans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan("com.excilys.persistence")
public class SpringJdbcConfig {
		@Bean
		public HikariDataSource myDataSource() {
			HikariConfig config = new HikariConfig("/hikari.properties");
			HikariDataSource ds = new HikariDataSource(config);
			
			return ds;			
		}
}
