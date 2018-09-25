package nl.nanda.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
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
@EnableJpaRepositories("nl.nanda.jpa")
@Import(ConfigurationProperties.class)
public class SystemConfig {

	@Autowired
	ConfigurationProperties properties;

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
		em.setJpaVendorAdapter(adapter());
		em.setPackagesToScan("nl.nanda.jpa");
		em.setJpaProperties(hibernateProperties());
		em.afterPropertiesSet();

		return em;
	}

	/**
	 * @return
	 */
	private JpaVendorAdapter adapter() {

		final HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true);
		adapter.setDatabase(Database.HSQL);
		return adapter;
	}

	/**
	 * @return
	 */
	private Properties hibernateProperties() {

		Properties props = new Properties();
		props.put("hibernate.dialect", properties.getDialect());
		props.put("hibernate.hbm2ddl.auto", properties.getHbm2ddl());
		props.put("hibernate.format_sql", properties.getFormat());
		props.put("javax.persistence.validation.mode", properties.getMode());
		props.put("hibernate.cache.use_second_level_cache", properties.getSecond_level_cache());
		props.put("hibernate.cache.region.factory_class", properties.getEhcache());
		props.put("hibernate.cache.use_query_cache", properties.getUse_query_cache());
		props.put("hibernate.cache.use_minimal_puts", properties.getUse_minimal_puts());
		return props;
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
	public DataSource dataSource() {

		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder().addScript("classpath:testdb/schema.sql")
		        .addScript("classpath:testdb/data.sql");
		builder.setType(EmbeddedDatabaseType.HSQL);
		builder.setScriptEncoding("UTF-8");
		return builder.build();

	}

	@Bean("dataSource")
	@Profile("start")
	public DataSource dataSourceA() {
		return new HikariDataSource(hikariConfig());
	}

	@Bean
	public HikariConfig hikariConfig() {
		HikariConfig config = new HikariConfig();

		config.setDriverClassName(properties.getDriverClassName());
		config.setJdbcUrl(properties.getJdbcUrl());
		config.setUsername(properties.getUsername());
		config.setPassword(properties.getPassword());

		config.setIdleTimeout(properties.getIdleTimeout());
		config.setConnectionTimeout(properties.getConnectionTimeout());
		config.setPoolName(properties.getPool_name());
		config.setMaximumPoolSize(properties.getPoolSize());

		return config;
	}

}
