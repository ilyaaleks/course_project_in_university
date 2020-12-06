package org.belstu.fakegram.FakeGram.controller;

import lombok.AllArgsConstructor;
import org.belstu.fakegram.FakeGram.domain.Post;
import org.belstu.fakegram.FakeGram.dto.PostDto;
import org.belstu.fakegram.FakeGram.dto.PostPageDto;
import org.belstu.fakegram.FakeGram.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/", allowedHeaders = "*")
@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {
    private PostService postService;
    @PostMapping("/posts")
    public PostDto savePost(@RequestParam(name = "file") MultipartFile file,
                            @RequestParam String authorLogin,
                            @RequestParam String hashTags,
                            @RequestParam String text) throws IOException {
        final Post post=new Post();
        final Post savePost = postService.savePost(post, file, hashTags);
        return savePost;
    }

    @GetMapping("/posts/{userId}")
    public String numberOfPosts(
            @PathVariable() long userId,
            @RequestParam(name = "count") boolean flag) {

        return Integer.toString(postService.countOfPosts(userId));

    }

    @GetMapping("/userposts/{userId}")
    public PostPageDto getUserPosts(@PathVariable() long userId,
                                    Pageable page) {
        final List<Post> userPosts = postService.getUserPosts(userId, page);
        return
    }

    @GetMapping("/postsByTag/{tagId}")
    public PostPageDto getPostsByTag(@PathVariable() long tagId,
                                     Pageable page)//,@RequestParam long tagId
    {
        return postService.getPostsByTag(tagId, page);
    }
}
