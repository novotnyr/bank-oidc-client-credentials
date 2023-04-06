package com.github.novotnyr.bank;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class OAuthConfiguration {
    @Bean
    ReactiveOAuth2AuthorizedClientManager authorizedClientManager(
            ReactiveClientRegistrationRepository clientRegistrations,
            ReactiveOAuth2AuthorizedClientService authorizedClientService) {

        return new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations, authorizedClientService);
    }

    @Bean
    public WebClient webClientSecurityCustomizer(ReactiveOAuth2AuthorizedClientManager authorizedClients) {
        var oAuthFilter = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClients);
        oAuthFilter.setDefaultClientRegistrationId("keycloak");

        return WebClient.builder()
                        .filter(oAuthFilter)
                        .build();
    }
}
