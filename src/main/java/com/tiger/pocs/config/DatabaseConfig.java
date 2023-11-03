package com.tiger.pocs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@Configuration
@EnableR2dbcAuditing
public class DatabaseConfig {

//    @Bean
//    ReactiveAuditorAware<String> auditorAware() {
//        return () -> ReactiveSecurityContextHolder.getContext()
//                .map(SecurityContext::getAuthentication)
//                .filter(Authentication::isAuthenticated)
//                .map(Authentication::getPrincipal)
//                .map(User.class::cast)
//                .map(User::getUsername);
//
//
//    }
}
