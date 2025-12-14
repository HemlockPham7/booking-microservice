package com.convit.apigateway.Filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class KeyAuthFilter extends AbstractGatewayFilterFactory<KeyAuthFilter.Config> {

    public KeyAuthFilter() {
        super(Config.class);
    }

    @Value("${apiKey}")
    private String apiKey;

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if(!exchange.getRequest().getHeaders().containsKey("apiKey")) {
                throw new RuntimeException("Missing authorization information");
            }
            String key = exchange.getRequest().getHeaders().get("apiKey").get(0);
            if (!key.equals(apiKey)) {
                throw new RuntimeException("Invalid apiKey");
            }
            ServerHttpRequest request = exchange.getRequest();
            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    static class Config {

    }
}
