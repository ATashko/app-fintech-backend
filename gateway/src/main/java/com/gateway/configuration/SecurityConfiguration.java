package com.gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {
	
	private final ReactiveClientRegistrationRepository reactiveClientRegistrationRepository;
	
    public SecurityConfiguration(ReactiveClientRegistrationRepository reactiveClientRegistrationRepository) {
		this.reactiveClientRegistrationRepository = reactiveClientRegistrationRepository;
	}

	@Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().authenticated()
                )
                .oauth2Login()
                .and()
                .logout()
                .logoutSuccessHandler(oidcServerLogoutSuccessHandler());
        
        return http.build();
    }
	
	private ServerLogoutSuccessHandler oidcServerLogoutSuccessHandler() {
		OidcClientInitiatedServerLogoutSuccessHandler oidcClientInitiatedServerLogoutSuccesHandler
		            = new OidcClientInitiatedServerLogoutSuccessHandler(reactiveClientRegistrationRepository);
		oidcClientInitiatedServerLogoutSuccesHandler.setPostLogoutRedirectUri("http://localhost:8080/login");
		return oidcClientInitiatedServerLogoutSuccesHandler;
	}
}
