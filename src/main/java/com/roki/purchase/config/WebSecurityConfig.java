package com.roki.purchase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        http.authorizeRequests().
                antMatchers("/web/login-form","/webjars/**","/web/login","/css/**",
                        "/js/**").permitAll();

//        http.authorizeRequests().antMatchers("/web/dashboard/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.formLogin().loginPage("/web/login-form")    //url din methoda de showFormLogin
                .loginProcessingUrl("/web/login")    //action din formular
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/web/main-page")
                .failureUrl("/web/login-form");

        http.logout().logoutUrl("/web/logout");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void globalConfiguration(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder)
            throws Exception{

        auth.jdbcAuthentication().dataSource(this.dataSource).passwordEncoder(passwordEncoder);
        System.out.println(passwordEncoder.encode("bogdan"));
    }
}
