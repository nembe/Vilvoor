package nl.nanda.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
// @EnableTransactionManagement
@ComponentScan("nl.nanda")
@Import(SystemConfig.class)
public class AccountsConfig {

    // @Autowired
    // LocalContainerEntityManagerFactoryBean entityManagerFactory;

}
