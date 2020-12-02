package org.belstu.fakegram.FakeGram.config;

import org.belstu.fakegram.FakeGram.domain.User;
import org.belstu.fakegram.FakeGram.repository.UserRepository;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.time.LocalDateTime;
import java.util.Map;

import static org.belstu.fakegram.FakeGram.config.EndpointKeys.HOME_ENDPOINT;
import static org.belstu.fakegram.FakeGram.config.UserOauthKeys.EMAIL;
import static org.belstu.fakegram.FakeGram.config.UserOauthKeys.GENDER;
import static org.belstu.fakegram.FakeGram.config.UserOauthKeys.ID;
import static org.belstu.fakegram.FakeGram.config.UserOauthKeys.LOCALE;
import static org.belstu.fakegram.FakeGram.config.UserOauthKeys.NAME;
import static org.belstu.fakegram.FakeGram.config.UserOauthKeys.PICTURE;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().mvcMatchers(HOME_ENDPOINT).permitAll()
                .anyRequest().authenticated().and().csrf().disable();
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserRepository userRepository) {
        return map -> {
            final String id = String.valueOf(map.get(ID));
            final User user = userRepository.findById(id).orElseGet(() -> fillUser(map));
            user.setLastVisit(LocalDateTime.now());
            return userRepository.save(user);
        };
    }

    private User fillUser(Map<String, Object> userDetails) {
        User user = new User();
        user.setId(String.valueOf(userDetails.get(ID)));
        user.setName(String.valueOf(userDetails.get(NAME)));
        user.setEmail(String.valueOf(userDetails.get(EMAIL)));
        user.setGender(String.valueOf(userDetails.get(GENDER)));
        user.setLocale(String.valueOf(userDetails.get(LOCALE)));
        user.setLocale(String.valueOf(userDetails.get(PICTURE)));
        return user;
    }
}
