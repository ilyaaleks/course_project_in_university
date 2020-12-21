package org.belstu.fakegram.FakeGram.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.belstu.fakegram.FakeGram.domain.HashTag;
import org.belstu.fakegram.FakeGram.domain.Post;
import org.belstu.fakegram.FakeGram.domain.User;
import org.belstu.fakegram.FakeGram.dto.PostDto;
import org.belstu.fakegram.FakeGram.dto.PostPageDto;
import org.belstu.fakegram.FakeGram.mapper.DtoConverter;
import org.belstu.fakegram.FakeGram.repository.HashTagRepository;
import org.belstu.fakegram.FakeGram.repository.PostRepository;
import org.belstu.fakegram.FakeGram.service.PostService;
import org.belstu.fakegram.FakeGram.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hibernate.internal.util.StringHelper.isEmpty;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    @Value("${upload.path}")
    private String uploadPath;

    private final PostRepository postRepository;
    private final HashTagRepository tagRepository;
    private final DtoConverter converter;
    private final UserService userService;

    @Override
    public PostPageDto getUserPosts(long userId, Pageable pageable) {
        Page<Post> page = postRepository.findByAuthor(userId, pageable);
        List<Post> postList = page.getContent();
        final List<PostDto> postDtoList = postList.stream().map(p -> {
            final PostDto postDto = converter.convertToPostDto(p);
            postDto.setId(p.getId());
            return postDto;
        }).collect(Collectors.toList());
        return new PostPageDto(postDtoList,
                pageable.getPageNumber(),
                page.getTotalPages());
    }

    @Override
    public PostPageDto getPostsByTag(long tagId, Pageable pageable) {
        Page<Post> page = postRepository.findByTag(tagId, pageable);
        List<Post> postList = page.getContent();
        final List<PostDto> postDtoList = postList.stream().map(converter::convertToPostDto).collect(Collectors.toList());
        return new PostPageDto(postDtoList,
                pageable.getPageNumber(),
                page.getTotalPages());
    }

    @Override
    public int countOfPosts(long userId) {
        return 0;
    }


    @Override
    public PostPageDto getSubscriptionPosts(long userId, Pageable pageable) {
        Page<Post> page = postRepository.findBySubscription(userId, pageable);
        List<Post> postList = page.getContent();
        final List<PostDto> postDtoList = postList.stream().map(p -> {
            final PostDto dto = converter.convertToPostDto(p);
            dto.setAuthorPhotoPath(p.getAuthor().getPhotoUrl());
            return dto;
        }).collect(Collectors.toList());
        return new PostPageDto(postDtoList,
                pageable.getPageNumber(),
                page.getTotalPages());
    }

    @SneakyThrows
    @Override
    @Transactional
    public Post update(MultipartFile file, long authorId, String hashTags, String text, long postId, User currentUser) {
        final Set<HashTag> tagList = new HashSet<>();
        final Post post = findById(postId);
        if (post == null || !post.getAuthor().getUsername().equals(getUsernameOfCurrentUser())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Access denied");
        }
        if (file != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            post.setPhotoPath(resultFilename);
            final Set<HashTag> tagSet = iterateTags(hashTags);
            if (!isEmpty(hashTags)) {
                post.getHashTags().clear();
                for (HashTag tag : tagSet) {
                    post.getHashTags().add(tag);
                }
            }
            post.setText(text);

        }
        return post;
    }

    @SneakyThrows
    @Override
    public Post savePost(long authorId, MultipartFile file, String hashTags, String text, Principal currentUser) {
        final Set<HashTag> tagList = new HashSet<>();
        final Post post = new Post();
        if (file != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            post.setPhotoPath(resultFilename);
            post.setText(text);
            post.setAuthor(userService.findById(authorId));
            if (!isEmpty(hashTags)) {
                final Set<HashTag> tags = iterateTags(hashTags);
                post.setHashTags(tags);
            }
            postRepository.save(post);
        }
        return post;
    }

    @Override
    public void delete(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post findById(long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Post can't be updated"));
    }

    private Set<HashTag> iterateTags(String hashTags) {
        Set<HashTag> tagSet = new HashSet<>();
        for (String tag : hashTags.split("#")) {
            final String trimString = tag.trim();
            HashTag currentTag = tagRepository.findByText(trimString);
            if (currentTag == null) {
                HashTag hashTag = new HashTag();
                hashTag.setText(trimString);
                currentTag = tagRepository.save(hashTag);
            }
            tagSet.add(currentTag);
        }
        return tagSet;
    }

    private String getUsernameOfCurrentUser() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
