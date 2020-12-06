package org.belstu.fakegram.FakeGram.service.impl;

import lombok.AllArgsConstructor;
import org.belstu.fakegram.FakeGram.domain.User;
import org.belstu.fakegram.FakeGram.dto.UserDto;
import org.belstu.fakegram.FakeGram.dto.UserPageDto;
import org.belstu.fakegram.FakeGram.mapper.DtoConverter;
import org.belstu.fakegram.FakeGram.repository.UserRepository;
import org.belstu.fakegram.FakeGram.service.MailSender;
import org.belstu.fakegram.FakeGram.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    @Value("server.url")
    private final String serverUrl;
    private final UserRepository userRepository;
    private final DtoConverter converter;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailSender mailSender;
    @Override
    public UserPageDto getSubscribers(long userId, Pageable pageable) {
        final Page<User> page = userRepository.findSubscribers(userId,pageable);
        final List<User> subscribers = page.getContent();
        final List<UserDto> userDtos = subscribers.stream().map(converter::convertToUserDto).collect(toList());
        return new UserPageDto(userDtos,
                pageable.getPageNumber(),
                page.getTotalPages());
    }

    @Override
    public UserPageDto getSubscriptions(long userId, Pageable pageable) {
        final Page<User> page = userRepository.findSubscriptions(userId,pageable);
        final List<User> subscribers = page.getContent();
        final List<UserDto> userDtos = subscribers.stream().map(converter::convertToUserDto).collect(toList());
        return new UserPageDto(userDtos,
                pageable.getPageNumber(),
                page.getTotalPages());
    }

    @Override
    public int getCountOfSubscribers(long userId) {
        return userRepository.findById(userId).getSubscriptions().size();
    }

    @Override
    public int getCountOfSubscriptions(long userId) {
        return userRepository.findById(userId).getSubscribers().size();
    }

    @Override
    public User subscribe(long userId, long id) {
        User user=userRepository.findById(userId);
        User currentUser=userRepository.findById(id);
        user.getSubscribers().add(currentUser);
        return userRepository.save(user);
    }

    @Override
    public User unsubscribe(long userId, long id) {
        User user=userRepository.findById(userId);
        User currentUser=userRepository.findById(id);
        user.getSubscribers().remove(currentUser);
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(@NotNull String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User register(@NotNull UserDto userDto) {
        User user = converter.convertToUser(userDto);
        User existUser = userRepository.findByUsername(user.getUsername());
        if (existUser != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "User is exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivate(false);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPhotoUrl("default.jpg");
        if (StringUtils.isEmpty(user.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Email is invalid");
        }
        String message = String.format("Hello to FakeGram, %s!\n" +
                        "We are glad to see you. Please, visit the following link: " + serverUrl + "registration/activate/%s",
                user.getUsername(),
                user.getActivationCode());
        mailSender.send(user.getEmail(), "Activation Code", message);
        User registeredUser = userRepository.save(user);
        return registeredUser;
    }

    @Override
    public User activateUser(@NotNull String code) {
        User user = userRepository.findByActivationCode(code);
        try {
            if (user == null) {
                throw new IllegalArgumentException("Invalid user activation code");
            }
            user.setActivationCode(null);
            user.setActivate(true);
            User activatedUser = userRepository.save(user);
            return activatedUser;
        }
        catch (IllegalArgumentException ex)
        {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Access denied, user already activated or not registered", ex);
        }
    }
    @Override
    public User updateUser(UserDto userDto) {
        if (!userDto.getUsername().equals(getUsernameOfCurrentUser())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Access denied");
        }
        User user = userRepository.findByUsername(userDto.getUsername());
        user.setLastName(userDto.getLastName());
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    private String getUsernameOfCurrentUser() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

}
