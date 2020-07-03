package com.game.config;

import com.game.filter.AuthenticationFilter;
import com.game.filter.EncodingFilter;
import com.game.filter.FilterToken;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private PlayerService playerService;
    private ApplicationContext context;

    public WebSecurityConfig(ApplicationContext context, PlayerService playerService) {
        this.playerService = playerService;
        this.context = context;
    }

    public ApplicationContext getContext() {
        return context;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        final AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManagerBean(), jwtConfig());
        httpSecurity
                .addFilterBefore(new FilterToken(jwtConfig(), playerService), UsernamePasswordAuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterBefore(new EncodingFilter(), ChannelProcessingFilter.class)
                .authorizeRequests()
                //Доступ только для не зарегистрированных пользователей
                .antMatchers("/player/reg").permitAll()//.not().fullyAuthenticated()
                //Доступ разрешен всем пользователей
                .antMatchers("/login").permitAll()
                //Все остальные страницы требуют аутентификации
                .anyRequest().permitAll()//.authenticated()
                .and()
                //Настройка для входа в систему
                .logout()
                .permitAll()
                .logoutSuccessUrl("/authorization");
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(playerService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }

    @Bean
    public ViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/view/");
        bean.setSuffix(".jsp");
        return bean;
    }
}