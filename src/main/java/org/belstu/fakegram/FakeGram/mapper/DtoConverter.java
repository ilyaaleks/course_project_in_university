package org.belstu.fakegram.FakeGram.mapper;

import org.belstu.fakegram.FakeGram.domain.User;
import org.belstu.fakegram.FakeGram.dto.UserDto;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class DtoConverter {
    public UserDto convertToUserDto(@NotNull User user) {
        return UserDto.builder().id(user.getId()).countOfPosts(user.getPosts().size()).build();
    }

    public User convertToUser(@NotNull UserDto userDto) {
        return new User(userDto.getName(), userDto.getSurname(), userDto.getEmail(), userDto.getAboutMe(), userDto.getLogin(), userDto.getPassword(), userDto.getRole(),
                userDto.getStatus(), userDto.getPhotoUrl());
    }
}
