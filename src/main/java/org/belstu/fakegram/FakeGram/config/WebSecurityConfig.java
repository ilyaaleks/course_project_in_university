package org.belstu.fakegram.FakeGram.config;

import org.belstu.fakegram.FakeGram.security.JwtAuthenticationEntryPoint;
import org.belstu.fakegram.FakeGram.security.JwtAuthenticationFilter;
import org.belstu.fakegram.FakeGram.service.impl.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private MyUserDetailsService userDetailsService;
    private JwtAuthenticationEntryPoint unauthorizedHandler;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private final String[] ENDPOINTS={"/api/auth/**", "/signup","/api/registration/**","/api/image/**","/api/registration","/api/registration/**"};

    public WebSecurityConfig(MyUserDetailsService userDetailsService, JwtAuthenticationEntryPoint unauthorizedHandler, BCryptPasswordEncoder bCryptPasswordEncoder, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }





    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(ENDPOINTS).permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
