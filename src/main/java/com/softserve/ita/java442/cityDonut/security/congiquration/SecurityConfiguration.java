package com.softserve.ita.java442.cityDonut.security.congiquration;

import com.mysql.cj.protocol.AuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@EnableWebSecurity
@EnableJpaRepositories(basePackages = "com")
@Configuration
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      //auth.userDetailsService(getUserDetailsService()).passwordEncoder(getPasswordEncoders());
        auth.inMemoryAuthentication().withUser("oleh")
                .password("oleh")
                .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/admin").access("hasRole('ADMIN')")
                .antMatchers("/user").hasAnyRole("ADMIN","USER","MODERATOR")
                .and().formLogin();
    }
    @Bean
    public PasswordEncoder getPasswordEncoders(){
        return NoOpPasswordEncoder.getInstance();
    };
    @Bean
    public UserDetailsService getUserDetailsService(){
        return new CustomUserDetailsService();
    };



}
