package org.belstu.fakegram.FakeGram.controller;

import lombok.AllArgsConstructor;
import org.belstu.fakegram.FakeGram.domain.User;
import org.belstu.fakegram.FakeGram.dto.UserDto;
import org.belstu.fakegram.FakeGram.dto.UserPageDto;
import org.belstu.fakegram.FakeGram.mapper.DtoConverter;
import org.belstu.fakegram.FakeGram.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@RestController()
@RequestMapping("api/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private DtoConverter converter;
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable() long id,
                                               @AuthenticationPrincipal User currentUser){
        User user = userService.findById(id);
        UserDto userDto = converter.convertToUserDto(user);
        if (user.getSubscribers().contains(currentUser)) {
            userDto.setSubscribed(true);
        } else {
            userDto.setSubscribed(false);
        }

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
                                      Pageable page){
        return userService.getSubscribers(userId, page);
    }

    @GetMapping("/subscriptions/{userId}")
    public UserPageDto getSubscriptions(@PathVariable() long userId,
                                        Pageable page){
        return userService.getSubscriptions(userId, page);
    }

    @GetMapping("/subscribe")
    public UserDto subscribe(@RequestParam int userId,
                             @AuthenticationPrincipal User currentUser) throws UserPrincipalNotFoundException {
        final User user = userService.subscribe(userId, currentUser.getId());
        return converter.convertToUserDto(user);
    }

    @GetMapping("/unsubscribe")
    public UserDto unsubscribe(@RequestParam int userId,
                               @AuthenticationPrincipal User currentUser) throws UserPrincipalNotFoundException {
        final User user = userService.unsubscribe(userId, currentUser.getId());
        return converter.convertToUserDto(user);
    }

}
