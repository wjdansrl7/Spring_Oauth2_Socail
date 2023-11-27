package com.demo.config;

import com.demo.auth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .antMatchers("/user/**").authenticated()
                    .antMatchers("/manager/**").access("hasRole('MANAGER') or hasRole('ADMIN')")
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().permitAll()
                .and()
                .oauth2Login()
                .loginPage("/loginForm")
                .defaultSuccessUrl("/user")
                .failureUrl("/loginForm")
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
//                .and()
//                .formLogin()
//                    .loginPage("/loginForm")
//                    .usernameParameter("id")
//                    .passwordParameter("pw")
//                    .loginProcessingUrl("/login")
//                    .defaultSuccessUrl("/")
//                    .failureUrl("/loginForm")
//                .and()
//                .logout()
//                    .logoutUrl("/logout")
//                    .logoutSuccessUrl("/");
    }
}
