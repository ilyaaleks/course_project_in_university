package org.belstu.fakegram.FakeGram.repository;

import org.belstu.fakegram.FakeGram.domain.Mark;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MarkRepository extends CrudRepository<Mark, Long> {
    @Query(value = "SELECT * FROM like_or_dislike l JOIN user u ON l.user_id=u.id JOIN posts p ON l.post_id=p.id  WHERE u.id=?2 AND p.id=?1", nativeQuery = true)
    Mark findByPostId(long postId, long userId);
}
