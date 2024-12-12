package org.example.a24_12_sparks.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    //Pour des sites externes
    @Bean
    @Qualifier("moviesAPIClient")
    public WebClient moviesAPIClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081/movies/")
                .build();
    }

    //Pour Utiliser avec le LoadBalancing et Eureka
    @Bean
    @Qualifier("moviesAPIClientWithLoadBalancing")
    @LoadBalanced
    public WebClient moviesAPIClientWithLoadBalancing(ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        return WebClient.builder()
                .baseUrl("http://MoviesService/movies/")
                .filter(lbFunction)
                .build();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}