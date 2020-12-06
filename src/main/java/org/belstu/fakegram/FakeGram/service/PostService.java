package org.belstu.fakegram.FakeGram.service;

import org.belstu.fakegram.FakeGram.domain.Post;
import org.belstu.fakegram.FakeGram.dto.PostPageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    Post savePost(Post post, MultipartFile file, String hashTags);

    List<Post> getUserPosts(long userId, Pageable page);

    PostPageDto getPostsByTag(long tagId, Pageable page);

    int countOfPosts(String userId);
}
