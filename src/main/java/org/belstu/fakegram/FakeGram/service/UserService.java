package org.belstu.fakegram.FakeGram.service;

import org.belstu.fakegram.FakeGram.domain.User;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface UserService {
    Set<User> getSubscribers(long userId, Pageable page);

    Set<User> getSubscriptions(long userId, Pageable page);

    int getCountOfSubscribers(long userId);

    int getCountOfSubscribtions(long userId);

    User subscribe(int userId, String id);

    User unsubscribe(int userId, String id);
}
