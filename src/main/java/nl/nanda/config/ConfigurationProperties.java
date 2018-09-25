package nl.nanda.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:configprops.properties")
public class ConfigurationProperties {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Value("${spring.dataSource.hikari.rewriteBatchedStatements}")
	private String rewriteBatchedStatements;
	@Value("${spring.dataSource.hikari.cacheResultSetMetadata}")
	private String cacheResultSetMetadata;
	@Value("${spring.dataSource.hikari.cacheServerConfiguration}")
	private String cacheServerConfiguration;
	@Value("${spring.datasource.hikari.maxLifetime}")
	private String maxLifetime;

	@Value("${spring.dataSource.hikari.cachePrepStmts}")
	private String cachePrepStmts;
	@Value("${spring.dataSource.hikari.prepStmtCacheSize}")
	private String prepStmtCacheSize;
	@Value("${spring.datasource.hikari.connectionTimeout}")
	private long connectionTimeout;
	@Value("${spring.datasource.hikari.pool-name}")
	private String pool_name;
	@Value("${spring.dataSource.hikari.useServerPrepStmts}")
	private String useServerPrepStmts;
	@Value("${spring.datasource.hikari.idleTimeout}")
	private long idleTimeout;

	@Value("${spring.dataSource.hsqldb.dataSourceClassName}")
	private String driverClassName;
	@Value("${spring.dataSource.hsqldb.jdbcUrl}")
	private String jdbcUrl;
	@Value("${spring.dataSource.hsqldb.username}")
	private String username;
	@Value("${spring.dataSource.hsqldb.password}")
	private String password;

	@Value("${spring.datasource.hikari.poolSize}")
	private int poolSize;
	@Value("${spring.datasource.hikari.idle}")
	private int idle;

	@Value("${spring.datasource.dataSource}")
	private String dataSource;
	@Value("${spring.dataSource.hsqldb.schema}")
	private String schema;

	@Value("${spring.hibernate.dialect}")
	private String dialect;
	@Value("${spring.hibernate.hbm2ddl.auto}")
	private String hbm2ddl;
	@Value("${spring.hibernate.format_sql}")
	private String format;
	@Value("${spring.javax.persistence.validation.mode}")
	private String mode;
	@Value("${spring.hibernate.cache.use_second_level_cache}")
	private String second_level_cache;
	@Value("${spring.hibernate.cache.use_query_cache}")
	private String use_query_cache;
	@Value("${spring.hibernate.cache.use_minimal_puts}")
	private String use_minimal_puts;
	@Value("${spring.hibernate.cache.region.factory_class}")
	private String ehcache;

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public String getHbm2ddl() {
		return hbm2ddl;
	}

	public void setHbm2ddl(String hbm2ddl) {
		this.hbm2ddl = hbm2ddl;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getSecond_level_cache() {
		return second_level_cache;
	}

	public void setSecond_level_cache(String second_level_cache) {
		this.second_level_cache = second_level_cache;
	}

	public String getUse_query_cache() {
		return use_query_cache;
	}

	public void setUse_query_cache(String use_query_cache) {
		this.use_query_cache = use_query_cache;
	}

	public String getUse_minimal_puts() {
		return use_minimal_puts;
	}

	public void setUse_minimal_puts(String use_minimal_puts) {
		this.use_minimal_puts = use_minimal_puts;
	}

	public String getCachePrepStmts() {
		return cachePrepStmts;
	}

	public void setCachePrepStmts(String cachePrepStmts) {
		this.cachePrepStmts = cachePrepStmts;
	}

	public String getPrepStmtCacheSize() {
		return prepStmtCacheSize;
	}

	public void setPrepStmtCacheSize(String prepStmtCacheSize) {
		this.prepStmtCacheSize = prepStmtCacheSize;
	}

	public long getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(long connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public String getPool_name() {
		return pool_name;
	}

	public void setPool_name(String pool_name) {
		this.pool_name = pool_name;
	}

	public String getUseServerPrepStmts() {
		return useServerPrepStmts;
	}

	public void setUseServerPrepStmts(String useServerPrepStmts) {
		this.useServerPrepStmts = useServerPrepStmts;
	}

	public long getIdleTimeout() {
		return idleTimeout;
	}

	public void setIdleTimeout(long idleTimeout) {
		this.idleTimeout = idleTimeout;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRewriteBatchedStatements() {
		return rewriteBatchedStatements;
	}

	public void setRewriteBatchedStatements(String rewriteBatchedStatements) {
		this.rewriteBatchedStatements = rewriteBatchedStatements;
	}

	public String getCacheResultSetMetadata() {
		return cacheResultSetMetadata;
	}

	public void setCacheResultSetMetadata(String cacheResultSetMetadata) {
		this.cacheResultSetMetadata = cacheResultSetMetadata;
	}

	public String getCacheServerConfiguration() {
		return cacheServerConfiguration;
	}

	public void setCacheServerConfiguration(String cacheServerConfiguration) {
		this.cacheServerConfiguration = cacheServerConfiguration;
	}

	public String getMaxLifetime() {
		return maxLifetime;
	}

	public void setMaxLifetime(String maxLifetime) {
		this.maxLifetime = maxLifetime;
	}

	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	public int getIdle() {
		return idle;
	}

	public void setIdle(int idle) {
		this.idle = idle;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getEhcache() {
		return ehcache;
	}

	public void setEhcache(String ehcache) {
		this.ehcache = ehcache;
	}

}
