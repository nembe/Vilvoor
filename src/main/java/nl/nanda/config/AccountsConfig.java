package nl.nanda.config;

import javax.validation.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * The Class AccountsConfig configure the service layer beans.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan({ "nl.nanda.service", "nl.nanda.domain" })
@Import({ SystemConfig.class, AspectsConfig.class })
public class AccountsConfig {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {

        final MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        processor.setValidator(validator());
        return processor;
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

}
