package edu.neu.CustomerManagement.Security;

import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@EnableTransactionManagement
@ComponentScan(basePackages = "edu.neu.CustomerManagement")
@PropertySource("/resources/persistence-mysql.properties")
public class CMSConfiguration implements WebMvcConfigurer {

	// set up variable to hold the properties

	@Autowired
	private Environment env;

	// set up a logger for diagnostics

	private Logger logger = Logger.getLogger(getClass().getName());

	// define a bean for ViewResolver

	@Bean
	public ViewResolver viewResolver() {

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(CMSDataSource());
		sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
		sessionFactory.setHibernateProperties(getHibernateProperties());
		return sessionFactory;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	/*
	 * private Properties hibernateProperties() {
	 * 
	 * private static final long serialVersionUID = 1L;
	 * 
	 * 
	 * 
	 * setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
	 * setProperty("hibernate.show_sql",env.getProperty("hibernate.show_sql"));
	 * 
	 * } }; }
	 */

	private Properties getHibernateProperties() {
		// set hibernate properties
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		return props;
	}

	// define a bean for our security datasource

	@Bean
	public DataSource CMSDataSource() {

		// create connection pool
		ComboPooledDataSource cmsDataSource = new ComboPooledDataSource();

		// set the jdbc driver class

		try {
			cmsDataSource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException exc) {
			throw new RuntimeException(exc);
		}

		// log the connection props
		// for sanity's sake, log this info
		// just to make sure we are REALLY reading data from properties file

		logger.info(">>> jdbc.url=" + env.getProperty("jdbc.url"));
		logger.info(">>> jdbc.user=" + env.getProperty("jdbc.user"));

		// set database connection props

		cmsDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		cmsDataSource.setUser(env.getProperty("jdbc.user"));
		cmsDataSource.setPassword(env.getProperty("jdbc.password"));

		// set connection pool props

		cmsDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));

		cmsDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));

		cmsDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));

		cmsDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));

		return cmsDataSource;
	}

	// need a helper method
	// read environment property and convert to int

	private int getIntProperty(String propName) {

		String propVal = env.getProperty(propName);

		// now convert to int
		int intPropVal = Integer.parseInt(propVal);

		return intPropVal;
	}

}