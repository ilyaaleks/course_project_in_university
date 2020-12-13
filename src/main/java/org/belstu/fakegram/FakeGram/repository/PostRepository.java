package org.belstu.fakegram.FakeGram.repository;

import org.belstu.fakegram.FakeGram.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post,Long> {
    @Query(value = "select * from posts p join user u on p.user_id=u.id where u.id = ?1", nativeQuery = true)
    Page<Post> findByAuthor(long userId, Pageable page);
    @Query(value = "select * from posts p join hashtag_table h on p.id=h.post_id join hash_tag t on t.id=h.tag_id  where t.id = ?1", nativeQuery = true)
    Page<Post> findByTag(long tagId, Pageable page);
    @Query(value = "SELECT * FROM posts p JOIN user_subscriptions us ON p.user_id=us.channel_id WHERE us.subscriber_id=?1", nativeQuery = true)
    Page<Post> findBySubscription(long userId, Pageable pageable);
}
