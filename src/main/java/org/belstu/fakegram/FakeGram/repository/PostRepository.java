package org.belstu.fakegram.FakeGram.repository;

import org.belstu.fakegram.FakeGram.domain.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post,Long> {
}
