package nl.nanda.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The Class AccountsConfig configure the service layer beans.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("nl.nanda.service")
@Import({ SystemConfig.class, AspectsConfig.class })
public class AccountsConfig {

}
