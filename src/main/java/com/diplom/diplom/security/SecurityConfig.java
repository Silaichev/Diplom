package com.diplom.diplom.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.inMemoryAuthentication()
                .withUser("admin")
                .password("q2w1e4r3")
                .authorities("ADMIN");*/

        auth.jdbcAuthentication()
                .dataSource(dataSource)
        .usersByUsernameQuery(
                "select username,password, 'true' from user "+
                        "where username =?"
        ).authoritiesByUsernameQuery(
                "select username, authority from user\n" +
                        "    join user_authorities ua on ua.id = user.user_authorities_id " +
                        "where username=?")
        .passwordEncoder(new BCryptPasswordEncoder());



    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/register","/login")
                .permitAll()
                .antMatchers("/**")
                .hasRole("ADMIN")
                .and().formLogin();


        http.csrf().disable();

    }
    //Это для RegistrationController
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
