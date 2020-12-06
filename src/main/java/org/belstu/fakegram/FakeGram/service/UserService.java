package org.belstu.fakegram.FakeGram.service;

import org.belstu.fakegram.FakeGram.domain.User;
import org.belstu.fakegram.FakeGram.dto.UserDto;
import org.belstu.fakegram.FakeGram.dto.UserPageDto;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserPageDto getSubscribers(long userId, Pageable page);

    UserPageDto getSubscriptions(long userId, Pageable page);

    int getCountOfSubscribers(long userId);

    int getCountOfSubscriptions(long userId);

    User subscribe(long userId, long id);

    User unsubscribe(long userId, long id);

    User findByUsername(String username);

    User register(UserDto user);

    User activateUser(String code);

    User updateUser(UserDto userDto);
}
