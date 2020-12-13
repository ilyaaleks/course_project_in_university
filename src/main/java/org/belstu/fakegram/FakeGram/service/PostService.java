package org.belstu.fakegram.FakeGram.service;

import org.belstu.fakegram.FakeGram.domain.Post;
import org.belstu.fakegram.FakeGram.domain.User;
import org.belstu.fakegram.FakeGram.dto.PostPageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    PostPageDto getUserPosts(long userId, Pageable page);

    PostPageDto getPostsByTag(long tagId, Pageable page);

    int countOfPosts(long userId);

    Post savePost(long authorId, MultipartFile file, String hashTags, String text, User currentUser);

    PostPageDto getSubscriptionPosts(long userId, Pageable pageable);

    Post update(MultipartFile file, long authorId, String hashTags, String text, long postId, User currentUser);

    void delete(long id);

    Post findById(long postId);
}
