package com.stver8935.user_service.configs;

import com.stver8935.user_service.models.UserRole;
import com.stver8935.rest_api.security.jwt.*;
import com.stver8935.user_service.security.services.UserService;
import com.stver8935.user_service.utils.ExceptionMessageAccessor;
import com.stver8935.user_service.utils.GeneralMessageAccessor;
import com.stver8935.user_service.security.jwt.JwtAuthenticationEntryPoint;
import com.stver8935.user_service.security.jwt.LoginFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @author : hangihyeong
 * @ packageName : com.stver8935.rest_api.configs
 * @ fileName : SecurityConfig
 * @since : 24. 8. 14.
 */

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserService userService;
    private final ExceptionMessageAccessor exceptionMessageAccessor;
    private final GeneralMessageAccessor generalMessageAccessor;

    public final  String[] permmitAllPath = {
            "/login/**",
            "/registration/**",
            "/user/login/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/actuator/**"};

    private final String[] userPath = {
            "/user/logout/**",
            "/user/info/**",
    };

    public final String[] adminPath = {

    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(CsrfConfigurer::disable)
                .cors(CorsConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(permmitAllPath).permitAll()
                        .requestMatchers(adminPath).hasRole(UserRole.ADMIN.name())
                        .requestMatchers(userPath).hasRole(UserRole.USER.name())
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                // /login 경로로 접근 시 동작
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration),userService,exceptionMessageAccessor),UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handler -> handler.authenticationEntryPoint(unauthorizedHandler))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }



}
