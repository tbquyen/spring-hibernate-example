package com.github.tbquyen.config;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

	@Bean
	public PlatformTransactionManager hibernateTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory().getObject());
		return transactionManager;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.put("hibernate.show_sql", true);
		properties.put("current_session_context_class", "thread");
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setPackagesToScan(new String[] { "com.github.tbquyen.entity" });
		factoryBean.setDataSource(dataSource());
		factoryBean.setHibernateProperties(properties);
		return factoryBean;
	}

	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://remotemysql.com:3306/jBpsPO7FJ6");
		dataSource.setUsername("jBpsPO7FJ6");
		dataSource.setPassword("CU7EUAIvdh");
		dataSource.setMaxIdle(2);
		dataSource.setTestWhileIdle(true);
		dataSource.setValidationQuery("SELECT 1");
		return dataSource;
	}
}