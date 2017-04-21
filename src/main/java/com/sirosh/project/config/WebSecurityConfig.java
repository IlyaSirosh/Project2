package com.sirosh.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

/**
 * Created by Illya on 4/20/17.
 */
//@Configuration
//@ComponentScan("com.sirosh.project.config")
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    private static final String ADMIN = "ADMIN_ROLE";
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Autowired
//    private Md5PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private JwtAuthenticationEntryPoint unauthorizedHandler;
//
//
//
//
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//
//        httpSecurity
//                .csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST,"/login","/user").anonymous()
//                .antMatchers("/**").authenticated()
//                .antMatchers(HttpMethod.PUT,"/dish/type","/ingredient/type").hasRole(ADMIN)
//                .antMatchers(HttpMethod.POST,"/dish/type","/ingredient/type").hasRole(ADMIN)
//                .antMatchers(HttpMethod.DELETE,"/dish/type","/ingredient/type").hasRole(ADMIN);
//
//
//
//    }
//
//}
