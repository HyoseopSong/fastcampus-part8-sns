package com.fastcampus.sns.configuration;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class AuthenticationConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    return http
//            .authorizeHttpRequests(auth -> auth
//                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                    .mvcMatchers("/api/**").permitAll()
//                    .mvcMatchers(
//                            HttpMethod.GET,
//                            "/",
//                            "/articles",
//                            "/articles/search-hashtag"
//                    ).permitAll()
//                    .anyRequest().authenticated()
//            )
//            .formLogin(withDefaults())
//            .logout(logout -> logout.logoutSuccessUrl("/"))
//            .oauth2Login(oAuth -> oAuth
//                    .userInfoEndpoint(userInfo -> userInfo
//                            .userService(oAuth2UserService)
//                    )
//            )
//            .csrf(csrf -> csrf.ignoringAntMatchers("/api/**"))
//            .build();

    return http
            .authorizeHttpRequests()
            .requestMatchers("/api/*/users/join", "/api/*/users/login").permitAll()
            .requestMatchers("/api/**").authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .addFilterBefore(new JwtTokenFilter(key, userService), UsernamePasswordAuthenticationFilter.class)
//            .exceptionHandling()
//            .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            .and()
            .csrf().disable().build();
  }
}
