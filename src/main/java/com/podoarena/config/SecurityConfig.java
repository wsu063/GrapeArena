package com.podoarena.config;

import com.podoarena.handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final DefaultOAuth2UserService oAuth2UserService;
private final OAuth2SuccessHandler oAuth2SuccessHandler;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        // 1. 페이지 접근에 대한 설정(인가)
        httpSecurity.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/images/**", "/fonts/**","/plugins/**").permitAll()
                        .requestMatchers("/", "/members/**", "/goods/**", "/concerts/**","/orders/**","/reserves/**").permitAll()
                        .requestMatchers("/favicon.ico", "/error").permitAll()
                        .requestMatchers("/members/carts/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                )        // 2. 로그인에 관한 설정
                .formLogin(formLogin -> formLogin
                        .loginPage("/members/login") // 로그인 페이지 URL
                        .defaultSuccessUrl("/") // 로그인 성공시 이동할 페이지 URL
                        .usernameParameter("email") // ★로그인시 id로 사용할 파라미터 이름(내 사이트에 맞는걸로)
                        .failureUrl("/members/login/error") // 로그인 실패시 이동할 페이지
                )        // 3. 로그아웃에 관한 설정
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) //로그아웃시 이동할 URL
                        .logoutSuccessUrl("/") // 로그아웃 성공시 이동할 URL
                ) // 4. 인증되지 않은 사용자의 접근에 관한 설정
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedPage("/members/login") // 인증되지 않은 사용자 접근 시 이동할 페이지
                ) // 로그인 이후 세션을 통해 로그인 유지
                .rememberMe(Customizer.withDefaults())
                .oauth2Login(oauth2 -> oauth2
                        //.authorizationEndpoint(endpoint -> endpoint.baseUri("/login/oauth2"))
                        .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
                        .userInfoEndpoint(endpoint -> endpoint.userService(oAuth2UserService))
                        //.successHandler(oAuth2SuccessHandler)
                );


        return httpSecurity.build();

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
