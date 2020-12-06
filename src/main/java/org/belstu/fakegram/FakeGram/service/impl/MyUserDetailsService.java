package org.belstu.fakegram.FakeGram.service.impl;

import org.belstu.fakegram.FakeGram.domain.User;
import org.belstu.fakegram.FakeGram.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserService userService;
    private User activeUser;

    public MyUserDetailsService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        activeUser=user;
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Invalid username");
        }
        if(user.isActivate()==false)
        {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Access denied, user not activated");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }
    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return authorities;
    }

    public User getUser() {
        return activeUser;
    }
}
