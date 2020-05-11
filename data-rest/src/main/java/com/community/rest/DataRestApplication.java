package com.community.rest;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DataRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataRestApplication.class, args);
    }

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)  // @prePost 어노테이션 사용 허용
    @EnableWebSecurity  // 웹용 시큐리티를 활성화
    static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // CORS(교차 출처 자원 공유) 설정 - 다른 출처에서 API 서버에 자원 공유 요청 하는 것을 허용
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.addAllowedOrigin("*");    // Origin을 모든 경로에 대해 허용
            configuration.addAllowedMethod("*");    // Method를 모든 경로에 대해 허용
            configuration.addAllowedHeader("*");    // Hader를 모든 경로에 대해 허용

            // ConfigurationSource 설정을 등록
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);

            http.httpBasic()
                    .and().authorizeRequests()
                    .anyRequest().permitAll()
                    // 스프링 시큐리티에 설정 추가
                    .and().cors().configurationSource(source)
                    .and().csrf().disable();
        }

        @Bean
        InMemoryUserDetailsManager userDetailsManager() {
            // 메서드 권한 제한 테스트에 사용할 유저 정보 2개를 추가
            User.UserBuilder comonUser = User.withUsername("commonUser").password("{noop}common").roles("USER");

            // havi 는 USER, ADMIN 권한을 가짐
            User.UserBuilder havi = User.withUsername("havi").password("{noop}test").roles("USER, ADMIN");

            List<UserDetails> userDetailsList = new ArrayList<>();
            userDetailsList.add(comonUser.build());
            userDetailsList.add(havi.build());

            return new InMemoryUserDetailsManager();
        }

    }

}