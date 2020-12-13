package org.belstu.fakegram.FakeGram.repository;

import org.belstu.fakegram.FakeGram.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(long id);
    Optional<User> findByActivationCode(String activationCode);
    @Query(value = "SELECT * FROM user u JOIN user_subscriptions us ON u.id = us.channel_id JOIN user u1 \n" +
            "  ON us.subscriber_id=u1.id WHERE us.subscriber_id=?1", nativeQuery = true)
    Page<User> findSubscriptions(long userId, Pageable page);

    @Query(value = "SELECT * FROM user u JOIN user_subscriptions us ON u.id = us.subscriber_id JOIN user u1 \n" +
            "  ON us.channel_id=u1.id WHERE us.channel_id=?1", nativeQuery = true)
    Page<User> findSubscribers(long userId, Pageable page);
}
