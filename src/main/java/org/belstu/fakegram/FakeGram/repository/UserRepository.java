package org.belstu.fakegram.FakeGram.repository;

import org.belstu.fakegram.FakeGram.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
