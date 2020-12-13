package org.belstu.fakegram.FakeGram.controller;

import lombok.AllArgsConstructor;
import org.belstu.fakegram.FakeGram.domain.Post;
import org.belstu.fakegram.FakeGram.domain.User;
import org.belstu.fakegram.FakeGram.dto.PostDto;
import org.belstu.fakegram.FakeGram.dto.PostPageDto;
import org.belstu.fakegram.FakeGram.dto.UserDto;
import org.belstu.fakegram.FakeGram.mapper.DtoConverter;
import org.belstu.fakegram.FakeGram.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:4200/", allowedHeaders = "*")
@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {
    private PostService postService;
    private DtoConverter converter;

    @PostMapping("/posts")
    public PostDto savePost(@RequestParam(name = "file") MultipartFile file,
                            @RequestParam long authorId,
                            @RequestParam String hashTags,
                            @RequestParam String text,
                            @AuthenticationPrincipal User currentUser) throws IOException {
        final Post savedPost = postService.savePost(authorId, file, hashTags, text, currentUser);
        return converter.convertToPostDto(savedPost);
    }

    @GetMapping("/posts/{userId}")
    public UserDto numberOfPosts(
            @PathVariable() long userId) {
        final int countOfPosts = postService.countOfPosts(userId);
        return UserDto.builder().countOfPosts(countOfPosts).build();

    }

    @GetMapping("/userposts/{userId}")
    public PostPageDto getUserPosts(@PathVariable() long userId,
                                    Pageable page) {
        return postService.getUserPosts(userId, page);
    }

    @GetMapping("/postsByTag/{tagId}")
    public PostPageDto getPostsByTag(@PathVariable() long tagId,
                                     Pageable page) {
        return postService.getPostsByTag(tagId, page);
    }

    @GetMapping("/subscriptionPosts/{userId}")
    public PostPageDto getSubscriptionPosts(@PathVariable long userId, Pageable pageable) {
        return postService.getSubscriptionPosts(userId, pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deletePost(@PathVariable long id) {
        postService.delete(id);
    }

    @PutMapping("")
    public PostDto updatePost(@RequestParam(name = "file") MultipartFile file,
                              @RequestParam long authorId,
                              @RequestParam String hashTags,
                              @RequestParam String text,
                              @RequestParam long postId,
                              @AuthenticationPrincipal User currentUser) {
        final Post post = postService.update(file, authorId, hashTags, text, postId, currentUser);
        return converter.convertToPostDto(post);
    }
}
