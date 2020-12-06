package org.belstu.fakegram.FakeGram.service.impl;

import org.belstu.fakegram.FakeGram.domain.Post;
import org.belstu.fakegram.FakeGram.dto.PostPageDto;
import org.belstu.fakegram.FakeGram.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Override
    public Post savePost(Post post, MultipartFile file, String hashTags) {
        return null;
    }

    @Override
    public List<Post> getUserPosts(long userId, Pageable page) {
        return null;
    }

    @Override
    public PostPageDto getPostsByTag(long tagId, Pageable page) {
        return null;
    }

    @Override
    public int countOfPosts(String userId) {
        return 0;
    }
}
