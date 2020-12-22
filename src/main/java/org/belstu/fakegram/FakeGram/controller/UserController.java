package org.belstu.fakegram.FakeGram.controller;

import lombok.AllArgsConstructor;
import org.belstu.fakegram.FakeGram.domain.User;
import org.belstu.fakegram.FakeGram.dto.AuthToken;
import org.belstu.fakegram.FakeGram.dto.ImagePath;
import org.belstu.fakegram.FakeGram.dto.UserDto;
import org.belstu.fakegram.FakeGram.dto.UserPageDto;
import org.belstu.fakegram.FakeGram.mapper.DtoConverter;
import org.belstu.fakegram.FakeGram.security.TokenProvider;
import org.belstu.fakegram.FakeGram.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.nio.file.attribute.UserPrincipalNotFoundException;

@RestController()
@RequestMapping("api/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private DtoConverter converter;
    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable() long id,
                                               @AuthenticationPrincipal User currentUser) {
        User user = userService.findById(id);
        UserDto userDto = converter.convertToUserDto(user);
        if (user.getSubscribers().contains(currentUser)) {
            userDto.setSubscribed(true);
        } else {
            userDto.setSubscribed(false);
        }
        userDto.setPhotoUrl(user.getPhotoUrl());
        return ResponseEntity.ok(userDto);
    }

    @GetMapping()
    public ResponseEntity<UserDto> getUserByUsername(@RequestParam() String username,
                                                     @AuthenticationPrincipal User currentUser) {
        User user = userService.findByUsername(username);
        UserDto userDto = converter.convertToUserDto(user);
        if (user.getSubscribers().stream().map(User::getUsername).anyMatch((u) -> u.equals(currentUser.getUsername()))) {
            userDto.setSubscribed(true);
        } else {
            userDto.setSubscribed(false);
        }
        userDto.setPhotoUrl(user.getPhotoUrl());
        return ResponseEntity.ok(userDto);
    }


    @GetMapping("/subscribers/count/{userId}")
    public UserDto getCountOfSubscribers(@PathVariable() long userId) {
        final int countOfSubscribers = userService.getCountOfSubscribers(userId);
        return UserDto.builder().countOfSubscribers(countOfSubscribers).build();
    }

    @GetMapping("/subscriptions/count/{userId}")
    public UserDto getCountOfSubscribtions(@PathVariable() long userId) {
        final int countOfSubscriptions = userService.getCountOfSubscriptions(userId);
        return UserDto.builder().countOfSubscriptions(countOfSubscriptions).build();
    }

    @GetMapping("/subscribers/{userId}")
    public UserPageDto getSubscribers(@PathVariable() long userId,
                                      Pageable page) {
        return userService.getSubscribers(userId, page);
    }

    @GetMapping("/subscriptions/{userId}")
    public UserPageDto getSubscriptions(@PathVariable() long userId,
                                        Pageable page) {
        return userService.getSubscriptions(userId, page);
    }

    @GetMapping("/subscribe")
    public UserDto subscribe(@RequestParam int userId,
                             @AuthenticationPrincipal User currentUser) throws UserPrincipalNotFoundException {
        final User user = userService.subscribe(userId, currentUser.getId());
        final UserDto userDto = converter.convertToUserDto(user);
        if (user.getSubscribers().stream().map(User::getUsername).anyMatch((u) -> u.equals(currentUser.getUsername()))) {
            userDto.setSubscribed(true);
        } else {
            userDto.setSubscribed(false);
        }
        return userDto;
    }

    @GetMapping("/unsubscribe")
    public UserDto unsubscribe(@RequestParam int userId,
                               @AuthenticationPrincipal User currentUser) throws UserPrincipalNotFoundException {
        final User user = userService.unsubscribe(userId, currentUser.getId());
        final UserDto userDto = converter.convertToUserDto(user);
        if (user.getSubscribers().stream().map(User::getUsername).anyMatch((u) -> u.equals(currentUser.getUsername()))) {
            userDto.setSubscribed(true);
        } else {
            userDto.setSubscribed(false);
        }
        return userDto;
    }

    @PutMapping
    public ResponseEntity<AuthToken> updateUser(@Valid @RequestBody UserDto userDto) {
        User user = userService.updateUser(userDto);
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        userDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @PostMapping
    public ResponseEntity<ImagePath> updatePhoto(@RequestParam MultipartFile file, @RequestParam String username) {
        return ResponseEntity.ok(userService.updatePhoto(file, username));
    }
}
