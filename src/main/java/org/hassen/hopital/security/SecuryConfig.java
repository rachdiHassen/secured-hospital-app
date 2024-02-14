package org.hassen.hopital.security;


import org.hassen.hopital.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecuryConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       // PasswordEncoder passwordEncoder=passwordEncoder();
       // 1ere methode:in memory authentication
        /*
       auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("123")).roles("user","admin");
       auth.inMemoryAuthentication().withUser("user").password(passwordEncoder.encode("456")).roles("user");
        */
        /*
       // 2eme méthode: jdbc authentication
       auth.jdbcAuthentication()
               .dataSource(dataSource)
               .usersByUsernameQuery("select username as principal,password as credentials,active from users where username=?")
               .authoritiesByUsernameQuery("select username as principal,role as role from users_roles where username=?")
               .rolePrefix("Role_")
               .passwordEncoder(passwordEncoder);
        */
        auth.userDetailsService(userDetailsService);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin();
        http.headers().frameOptions().disable();
        http.authorizeRequests().antMatchers("/webjars/**","/h2-console/**").permitAll();
        http.authorizeRequests().antMatchers("/admin/**").hasAuthority("admin");
        http.authorizeRequests().antMatchers("/index/**").hasAuthority("user");

        http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/403");
    }

}
