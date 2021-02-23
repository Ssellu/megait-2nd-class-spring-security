package com.megait.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // WebSecurityConfigurerAdapter : 웹 시큐리시 설정 할 때 얘를 상속 받아서 정의

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.authorizeRequests()
                .mvcMatchers("/", "/info").permitAll();
        http.authorizeRequests()
                .mvcMatchers("/admin").hasRole("ADMIN");
        http.authorizeRequests()
                .anyRequest().authenticated();
        http.formLogin(); // /login
        http.httpBasic();*/
        http.authorizeRequests()
                .mvcMatchers("/", "/info", "/account/**").permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .and()
            .httpBasic();
    }

//    인메모리 유저 등록 방법2
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("test01").password("{noop}1234").roles("USER").and()
//                .withUser("test02").password("{noop}1234").roles("USER").and()
//                .withUser("test03").password("{noop}1234").roles("USER").and();
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
