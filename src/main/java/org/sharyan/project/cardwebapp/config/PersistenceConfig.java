package org.sharyan.project.cardwebapp.config;

import org.sharyan.project.cardwebapp.config.properties.PersistenceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.sharyan.project.cardwebapp.persistence.dao")
public class PersistenceConfig {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public DataSource dataSource(@Autowired PersistenceProperties persistenceProperties) {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(persistenceProperties.getDriverClassName());
        dataSource.setUrl(persistenceProperties.getUrl());
        dataSource.setUsername(persistenceProperties.getUsername());
        dataSource.setPassword(persistenceProperties.getPassword());
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired PersistenceProperties persistenceProperties) {
        final LocalContainerEntityManagerFactoryBean managerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        managerFactoryBean.setDataSource(dataSource(persistenceProperties));
        managerFactoryBean.setPackagesToScan("org.sharyan.project.cardwebapp.persistence.entity");
        managerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        managerFactoryBean.setJpaProperties(hibernateProperties(persistenceProperties));
        return managerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties hibernateProperties(PersistenceProperties persistenceProperties) {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", persistenceProperties.getHibernate().getHbm2ddl().getAuto());
        hibernateProperties.setProperty("hibernate.show_sql", Boolean.toString(persistenceProperties.getHibernate().isShowSql()));
        hibernateProperties.setProperty("hibernate.dialect", persistenceProperties.getHibernate().getDialect());
        return hibernateProperties;
    }
}
