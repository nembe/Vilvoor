package nl.nanda.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.cache.ehcache.EhCacheRegionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * The Class SystemConfig configure the beans for the persistence layer.
 */
@Configuration
@EnableJpaRepositories("nl.nanda")
//@EnableConfigurationProperties(DataSourceProperties.class)
//public class SystemConfig extends DataSourceAutoConfiguration {
public class SystemConfig {
	
    //	@NotEmpty
    //	private String url;
	
//	@Autowired
//	private DataSourceProperties properties;
	
	@Bean
	@Primary
	@ConfigurationProperties("app.datasource")
	public DataSourceProperties properties() {
		return new DataSourceProperties();
	}
	
	
	@Autowired
	DataSource dataSource;

    /**
     * Entity manager factory.
     *
     * @return the local container entity manager factory bean
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        // em.setPersistenceXmlLocation("classpath:META-INF/persistence.xml");
//        final Properties props = new Properties();
//        props.setProperty("hibernate.format_sql", "true");
//        props.setProperty("javax.persistence.validation.mode", "AUTO");
       /* props.setProperty("hibernate.cache.region.factory_class", EhCacheRegionFactory.class.getName());*/
//        props.setProperty("hibernate.cache.use_second_level_cache", "true");
//        props.setProperty("hibernate.cache.use_query_cache", "true");
//        props.setProperty("hibernate.cache.use_minimal_puts", "true");
//        props.setProperty("hibernate.hbm2ddl.auto","create");
//        props.setProperty("hibernate.hbm2ddl.import_files", "src/main/recources/data.sql");
        

        final HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setGenerateDdl(true);
        adapter.setDatabase(Database.HSQL);

        em.setJpaVendorAdapter(adapter);
        em.setPackagesToScan("nl.nanda");
//        em.setJpaProperties(props);
        em.afterPropertiesSet();

        return em;
    }
	
 
    
  
   
    /**
     * Transaction manager.
     *
     * @return the platform transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager() {

        final JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return txManager;
    }

    /**
     * Creates an in-memory "Accounts and Transactions" database populated with
     * test data.
     *
     * @return the data source
     */
    @Bean("dataSource")
    @Profile("test")
    public DataSource dataSourceA() {
    	
        return new EmbeddedDatabaseBuilder()
                .addScript("classpath:testdb/schema.sql")
                .addScript("classpath:testdb/data.sql").build();

    }
    
   

  @Bean
  @Profile("start") 
  @ConfigurationProperties("app.datasource")
  public DataSource  dataSource() {
//	  return new HikariDataSource(this);
//  	return properties().initializeDataSourceBuilder().type(HikariDataSource.class)
//  			.build();
  	return properties().initializeDataSourceBuilder().build();
  }

  
    
}
