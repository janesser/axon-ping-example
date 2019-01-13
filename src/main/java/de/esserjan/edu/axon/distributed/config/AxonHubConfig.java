package de.esserjan.edu.axon.distributed.config;

import org.axonframework.axonserver.connector.AxonServerConfiguration;
import org.axonframework.springboot.autoconfig.AxonServerAutoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @see org.axonframework.springboot.autoconfig.AxonAutoConfiguration
 * @see org.axonframework.springboot.autoconfig.AxonServerAutoConfiguration
 */
@Configuration
@AutoConfigureAfter(AxonServerAutoConfiguration.class)
public class AxonHubConfig {

    @Value("${axon.hub.clientId}")
    private String clientId;

    @Value("${axon.hub.servers:localhost}")
    private String servers;

    @Bean
    public AxonServerConfiguration axonServerConfiguration() {
        return AxonServerConfiguration.builder()
                .servers(servers)
                .clientId(clientId)
                .build();
    }
}
