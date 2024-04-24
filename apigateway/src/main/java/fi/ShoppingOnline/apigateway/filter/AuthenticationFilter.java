package fi.ShoppingOnline.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import com.google.common.net.HttpHeaders;

import fi.ShoppingOnline.apigateway.Util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> extracted(exchange, chain));

    }

    private Mono<Void> extracted(ServerWebExchange exchange, GatewayFilterChain chain) {
        // header contains token or not
        if (routeValidator.isSecured.test(exchange.getRequest())) {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("missing authorization header");
            }
            @SuppressWarnings("null")
            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7);
            }
            try {
                //REST call to AUTH service//                    template.getForObject("http://IDENTITY-SERVICE//validate?token" + authHeader, String.class);
                jwtUtil.validateToken(authHeader);

            } catch (Exception e) {
                log.info(e.getMessage());
                throw new RuntimeException("");
            }

        }
        return chain.filter(exchange);
    }

    public static class Config {

    }

}
