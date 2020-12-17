package org.belstu.fakegram.FakeGram.mapper;

import org.belstu.fakegram.FakeGram.domain.Comment;
import org.belstu.fakegram.FakeGram.domain.HashTag;
import org.belstu.fakegram.FakeGram.domain.Mark;
import org.belstu.fakegram.FakeGram.domain.Post;
import org.belstu.fakegram.FakeGram.domain.User;
import org.belstu.fakegram.FakeGram.dto.CommentDto;
import org.belstu.fakegram.FakeGram.dto.HashTagDto;
import org.belstu.fakegram.FakeGram.dto.MarkDto;
import org.belstu.fakegram.FakeGram.dto.PostDto;
import org.belstu.fakegram.FakeGram.dto.UserDto;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DtoConverter {
    public UserDto convertToUserDto(@NotNull User user) {
        return new UserDto(user.getId(), user.getLastName(), user.getName(), user.getUsername(), user.getEmail(), user.getUserDescription(), user.isActivate(), user.getStatus());
    }

    public User convertToUser(@NotNull UserDto userDto) {
        return new User(userDto.getLastName(), userDto.getName(), userDto.getUsername(), userDto.getPassword(), userDto.getEmail(), userDto.getUserDescription(), userDto.getActivate(), userDto.getStatus(), userDto.getPhotoUrl());
    }

    public PostDto convertToPostDto(Post post) {
        PostDto postDto = new PostDto(post.getAuthor().getId(), post.getAuthor().getUsername(), post.getPhotoPath(), post.getText(), new Date());
        postDto.setAuthorPhotoPath(post.getAuthor().getPhotoUrl());
        return postDto;
    }

    public Post convertToPost(@NotNull PostDto postDto, @NotNull User user) {
        return new Post(user, postDto.getPhotoPath(), postDto.getText(), postDto.getDate());
    }

    public Set<HashTagDto> convertToHashTagDto(@NotNull Set<HashTag> hashTags) {
        return hashTags.stream().map(this::hashTagDtoConverter).collect(Collectors.toSet());
    }

    public CommentDto convertToCommentDto(@NotNull Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(), comment.getDate(), comment.getPostComment().getId(),
                comment.getAuthor().getId(), comment.getAuthor().getPhotoUrl());
    }

    public Comment convertToComment(@NotNull CommentDto commentDto, @NotNull User user, @NotNull Post post) {
        return new Comment(user, commentDto.getText(), commentDto.getDate(), post);
    }

    public Mark convertToMark(@NotNull MarkDto markDto, @NotNull User author, @NotNull Post post) {
        return new Mark(post, author, markDto.getTypeOfVote(), new Date());
    }

    public MarkDto convertToMarkDto(@NotNull Mark mark) {
        return new MarkDto(mark.getPost().getId(), mark.getAuthor().getId(), mark.getTypeOfVote(), mark.getDate());

    }

    private HashTagDto hashTagDtoConverter(HashTag hashTag) {
        return new HashTagDto(hashTag.getId(), hashTag.getText());
    }

}
