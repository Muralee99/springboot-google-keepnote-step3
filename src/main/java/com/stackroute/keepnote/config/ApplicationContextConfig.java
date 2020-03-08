package com.stackroute.keepnote.config;

import java.util.Properties;

import javax.sql.DataSource;

import com.stackroute.keepnote.model.Reminder;
import com.stackroute.keepnote.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.Note;

/*This class will contain the application-context for the application.
 * Define the following annotations:
 * @Configuration - Annotating a class with the @Configuration indicates that the
 *                  class can be used by the Spring IoC container as a source of
 *                  bean definitions
 * @ComponentScan - this annotation is used to search for the Spring components amongst the application
 * @EnableWebMvc - Adding this annotation to an @Configuration class imports the Spring MVC
 * 				   configuration from WebMvcConfigurationSupport
 * @EnableTransactionManagement - Enables Spring's annotation-driven transaction management capability.
 *
 *
 * */
@ComponentScan(basePackages = {"com.stackroute.keepnote"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)
        })
@Configuration
@EnableTransactionManagement
public class ApplicationContextConfig {

    final DriverManagerDataSource dataSource = new DriverManagerDataSource();

	/*
     * Define the bean for DataSource. In our application, we are using MySQL as the
	 * dataSource. To create the DataSource bean, we need to know: 1. Driver class
	 * name 2. Database URL 3. UserName 4. Password
	 */

	/*
	 * Use this configuration while submitting solution in hobbes.
	 * dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
	 * dataSource.setUrl("jdbc:mysql://" + System.getenv("MYSQL_HOST") + ":3306/" +
	 * System.getenv("MYSQL_DATABASE")
	 * +"?verifyServerCertificate=false&useSSL=false&requireSSL=false");
	 * dataSource.setUsername(System.getenv("MYSQL_USER"));
	 * dataSource.setPassword(System.getenv("MYSQL_PASSWORD"));
	 */

/*	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/keepnote"+"?verifyServerCertificate=false&useSSL=false&requireSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		return dataSource;
	}
	*/
	/*
	 * create a getter for Hibernate properties here we have to mention 1. show_sql
	 * 2. Dialect 3. hbm2ddl
	 */

	/*@Bean(name = "dataSource")
	public DataSource getDataSource() {
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@//10.206.193.12:1521/bodevdb.global.trafigura.com");
		dataSource.setUsername("TRAF_TITAN_OWNER_DEV12");
		dataSource.setPassword("TRAF_TITAN_OWNER_DEV12");
		return dataSource;
	}

	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
		properties.put("hibernate.hbm2ddl.auto","create-drop");
		return properties;
	}*/

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://" + System.getenv("MYSQL_HOST") + ":3306/" + System.getenv("MYSQL_DATABASE")
                + "?verifyServerCertificate=false&useSSL=false&requireSSL=false");
        dataSource.setUsername(System.getenv("MYSQL_USER"));
        dataSource.setPassword(System.getenv("MYSQL_PASSWORD"));
        return dataSource;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        return properties;
    }


    /*
     * Define the bean for SessionFactory. Hibernate SessionFactory is the factory
     * class through which we get sessions and perform database operations.
     */
    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
        sessionBuilder.addProperties(getHibernateProperties());
        sessionBuilder.addAnnotatedClasses(Category.class);
        sessionBuilder.addAnnotatedClasses(Note.class);
        sessionBuilder.addAnnotatedClasses(Reminder.class);
        sessionBuilder.addAnnotatedClasses(User.class);
        return sessionBuilder.buildSessionFactory();
    }

    /*
     * Define the bean for Transaction Manager. HibernateTransactionManager handles
     * transaction in Spring. The application that uses single hibernate session
     * factory for database transaction has good choice to use
     * HibernateTransactionManager. HibernateTransactionManager can work with plain
     * JDBC too. HibernateTransactionManager allows bulk update and bulk insert and
     * ensures data integrity.
     */
    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(
            SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(
                sessionFactory);
        return transactionManager;
    }

}
