package nl.nanda.config;

import nl.nanda.monitor.AnanieMonitor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * The Class AspectsConfig.
 */
@Configuration
@ComponentScan(basePackages = "nl.nanda.monitor")
@EnableAspectJAutoProxy
public class AspectsConfig {

    /**
     * Monitor factory.
     *
     * @return the jamon monitor
     */
    @Bean
    public AnanieMonitor monitorFactory() {
        return new AnanieMonitor();
    }

}
