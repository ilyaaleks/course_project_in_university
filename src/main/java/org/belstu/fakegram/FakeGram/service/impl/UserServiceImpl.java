package org.belstu.fakegram.FakeGram.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.belstu.fakegram.FakeGram.domain.User;
import org.belstu.fakegram.FakeGram.dto.ImagePath;
import org.belstu.fakegram.FakeGram.dto.UserDto;
import org.belstu.fakegram.FakeGram.dto.UserPageDto;
import org.belstu.fakegram.FakeGram.mapper.DtoConverter;
import org.belstu.fakegram.FakeGram.repository.UserRepository;
import org.belstu.fakegram.FakeGram.service.MailSender;
import org.belstu.fakegram.FakeGram.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Value("${server.activation.url}")
    private String serverUrl;
    @Value("${upload.path}")
    private String uploadPath;
    @NonNull
    private final UserRepository userRepository;
    @NonNull
    private final DtoConverter converter;
    @NonNull
    private final BCryptPasswordEncoder passwordEncoder;
    @NonNull
    private final MailSender mailSender;


    @Override
    @Transactional
    public UserPageDto getSubscribers(long userId, Pageable pageable) {
        final Page<User> page = userRepository.findSubscribers(userId, pageable);
        final List<User> subscribers = page.getContent();
        final List<UserDto> userDtos = subscribers.stream().map(converter::convertToUserDto).collect(toList());
        return new UserPageDto(userDtos,
                pageable.getPageNumber(),
                page.getTotalPages());
    }

    @Override
    @Transactional
    public UserPageDto getSubscriptions(long userId, Pageable pageable) {
        final Page<User> page = userRepository.findSubscriptions(userId, pageable);
        final List<User> subscribers = page.getContent();
        final List<UserDto> userDtos = subscribers.stream().map(converter::convertToUserDto).collect(toList());
        return new UserPageDto(userDtos,
                pageable.getPageNumber(),
                page.getTotalPages());
    }

    @Override
    @Transactional
    public int getCountOfSubscribers(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found")).getSubscriptions().size();
    }

    @Override
    @Transactional
    public int getCountOfSubscriptions(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found")).getSubscribers().size();
    }

    @Override
    @Transactional
    public User subscribe(long userId, long id) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"));
        User currentUser = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"));
        user.getSubscribers().add(currentUser);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User unsubscribe(long userId, long id) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"));
        User currentUser = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"));
        user.getSubscribers().remove(currentUser);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User findByUsername(@NotNull String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"));
    }

    @Override
    @Transactional
    public User register(@NotNull UserDto userDto) {
        userDto.setPhotoUrl("default.jpg");
        User user = converter.convertToUser(userDto);
        Optional<User> existUser = userRepository.findByUsername(user.getUsername());
        if (existUser.isPresent()) {
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
    @Transactional
    public User activateUser(@NotNull String code) {
        User user = userRepository.findByActivationCode(code).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"));
        try {
            if (user == null) {
                throw new IllegalArgumentException("Invalid user activation code");
            }
            user.setActivationCode(null);
            user.setActivate(true);
            User activatedUser = userRepository.save(user);
            return activatedUser;
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Access denied, user already activated or not registered", ex);
        }
    }

    @Override
    @Transactional
    public User updateUser(UserDto userDto) {
        if (!userDto.getUsername().equals(getUsernameOfCurrentUser())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Access denied");
        }
        User user = userRepository.findByUsername(userDto.getUsername()).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"));
        user.setLastName(userDto.getLastName());
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"));
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

    @Override
    @Transactional
    public ImagePath updatePhoto(@NotNull MultipartFile file, @NotNull String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"));
        try {

            if (file == null || file.getOriginalFilename().isEmpty()) {
                throw new IllegalArgumentException("Problems with updating photos");
            } else if (user == null || !user.getUsername().equals(getUsernameOfCurrentUser())) {
                throw new AccessException("Access denied");
            }
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadPath + "/" + resultFilename));
                user.setPhotoUrl(resultFilename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Photo can't be updated", ex);
        } catch (AccessException ex) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Access denied", ex);
        }


        return new ImagePath(userRepository.save(user).getPhotoUrl());
    }
}
