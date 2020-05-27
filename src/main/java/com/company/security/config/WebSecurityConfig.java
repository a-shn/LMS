package com.company.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/uploadCourse");

        http.authorizeRequests()
                .antMatchers("/signup").permitAll()
                .antMatchers("/signin").permitAll()
                .antMatchers("/uploadfiles").authenticated()
                .antMatchers("/getfile").authenticated()
                .antMatchers("/courses").authenticated()
                .antMatchers("/course").authenticated()
                .antMatchers("/profile").authenticated()
                .antMatchers("/uploadCourse").authenticated()
                .antMatchers("/").authenticated()
                .and()
                .rememberMe().rememberMeParameter("remember-me").tokenRepository(persistentTokenRepository());

        http.formLogin()
                .loginPage("/signin")
                .usernameParameter("email")
                .passwordParameter("password")
                .failureUrl("/signin?error")
                .defaultSuccessUrl("/profile", true)
                .permitAll();

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("logout"))
                .logoutSuccessUrl("/signin")
                .deleteCookies("SESSION", "remember-me")
                .invalidateHttpSession(true);
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
}
