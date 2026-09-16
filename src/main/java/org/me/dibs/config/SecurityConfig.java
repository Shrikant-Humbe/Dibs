package org.me.dibs.config;

import org.me.dibs.constants.SecurityConfigConstant;
import org.me.dibs.service.OidcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    OidcService oidcUserSerivice;
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authprovider = new DaoAuthenticationProvider(userDetailsService);
        authprovider.setPasswordEncoder(new BCryptPasswordEncoder(SecurityConfigConstant.BCRYPT_STRENGTH.getIntValue()));
        return  authprovider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String frontendUrl = System.getenv("FRONTEND_URL");
        String successRedirectUrl = (frontendUrl != null) ? frontendUrl : SecurityConfigConstant.DEFAULT_FRONTEND_URL.getValue();

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request->
                        request.requestMatchers("/register","/login","/","/error")
                        .permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(outh2-> outh2
                        .userInfoEndpoint(userInfo ->userInfo.oidcUserService(oidcUserSerivice))
                        .defaultSuccessUrl(successRedirectUrl))
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new AjaxAwareAuthenticationEntryPoint("/oauth2/authorization/google")))
                .cors(Customizer.withDefaults())

        ;
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        String frontendUrl = System.getenv("FRONTEND_URL");
        List<String> allowedOrigins = frontendUrl != null ? List.of(frontendUrl, SecurityConfigConstant.DEFAULT_FRONTEND_ORIGIN.getValue()) : List.of(SecurityConfigConstant.DEFAULT_FRONTEND_ORIGIN.getValue());
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
