package com.endava.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Defines two roles - USER amd ADMIN - with different usernames and passwords.
     *
     * @param auth SecurityBuilder used to create an AuthenticationManager. Allows for easily building in memory authentication
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("grandUser").password(passwordEncoder().encode("using")).roles("USER").and()
                .withUser("megaAdmin").password(passwordEncoder().encode("admining")).roles("ADMIN");
    }

    /**
     * Sets the ednpoint access to the different roles.
     *
     * @param httpSecurity It allows configuring web based security for specific http requests.
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/user/delete/*", "user/find/*").hasRole("USER")
                .antMatchers("/user/**").hasRole("ADMIN")
                .anyRequest()
                .fullyAuthenticated()
                .and()
                .httpBasic();
        httpSecurity.csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
