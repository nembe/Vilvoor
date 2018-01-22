package nl.nanda.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
// @EnableTransactionManagement
@Import(SystemConfig.class)
public class AccountsConfig {

    // @Autowired
    // LocalContainerEntityManagerFactoryBean entityManagerFactory;

}
