package org.charllson.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClient() { //In layman terms, this is a buider that enable the load balancing feature. That is, it will try to find a server(may be server of multiple instance) to send the request.
        return WebClient.builder();
    }
}
