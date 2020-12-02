package org.belstu.fakegram.FakeGram.controller;

import lombok.AllArgsConstructor;
import org.belstu.fakegram.FakeGram.domain.User;
import org.belstu.fakegram.FakeGram.dto.UserDto;
import org.belstu.fakegram.FakeGram.dto.UserPageDto;
import org.belstu.fakegram.FakeGram.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Set;

@RestController()
@RequestMapping("api/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable() long id,
                                               @AuthenticationPrincipal User currentUser) throws UserPrincipalNotFoundException {
        User user=userService.findById(id);
        user.setPhotoUrl(user.getPhotoUrl());
        UserDto userDto=converter.convertToUserDto(user);
        if(user.getSubscribers().contains(currentUser))
        {
            userDto.setSubscribed(true);
        }
        else
        {
            userDto.setSubscribed(false);
        }

        return ResponseEntity.ok(userDto);
    }


    @PostMapping
    public User saveUser(@@RequestParam MultipartFile file,
                         @RequestParam String name,
                         @RequestParam String surname,
                         @RequestParam String aboutMe,
                         @RequestParam String login,
                         @RequestParam String password,
                         @RequestParam String email) throws IOException {


        return userService.save(user, fileStr, fileName);
    }

    @GetMapping("/subscribers/count/{userId}")
    public UserDto getCountOfSubscribers(@PathVariable() long userId) {
        final int countOfSubscribers = userService.getCountOfSubscribers(userId);
        return
    }

    @GetMapping("/subscriptions/count/{userId}")
    public UserDto getCountOfSubscribtions(@PathVariable() long userId) {
        final int countOfSubscribtions = userService.getCountOfSubscribtions(userId);
        return ;
    }
    @GetMapping("/subscribers/{userId}")
    public UserPageDto getSubscribers(@PathVariable() long userId,
                                      Pageable page) throws UserPrincipalNotFoundException {
        final Set<User> subscribers = userService.getSubscribers(userId, page);
        return
    }

    @GetMapping("/subscriptions/{userId}")
    public UserPageDto getSubscriptions(@PathVariable() long userId,
                                        Pageable page) throws UserPrincipalNotFoundException {
        final Set<User> subscriptions = userService.getSubscriptions(userId, page);
        return
    }
    @GetMapping("/subscribe")
    public UserDto subscribe(@RequestParam int userId,
                             @AuthenticationPrincipal User currentUser) throws UserPrincipalNotFoundException {
        final User user = userService.subscribe(userId, currentUser.getId());
        return

    }
    @GetMapping("/unsubscribe")
    public UserDto unsubscribe(@RequestParam int userId,
                               @AuthenticationPrincipal User currentUser) throws UserPrincipalNotFoundException {
        final User user = userService.unsubscribe(userId, currentUser.getId());
        return
    }
}
