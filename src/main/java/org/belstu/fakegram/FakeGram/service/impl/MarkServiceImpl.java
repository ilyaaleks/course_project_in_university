package org.belstu.fakegram.FakeGram.service.impl;

import lombok.AllArgsConstructor;
import org.belstu.fakegram.FakeGram.domain.Mark;
import org.belstu.fakegram.FakeGram.domain.Post;
import org.belstu.fakegram.FakeGram.domain.TypeOfVote;
import org.belstu.fakegram.FakeGram.domain.User;
import org.belstu.fakegram.FakeGram.dto.MarkDto;
import org.belstu.fakegram.FakeGram.mapper.DtoConverter;
import org.belstu.fakegram.FakeGram.repository.MarkRepository;
import org.belstu.fakegram.FakeGram.repository.PostRepository;
import org.belstu.fakegram.FakeGram.repository.UserRepository;
import org.belstu.fakegram.FakeGram.service.MarkService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Set;

@Service
@AllArgsConstructor
public class MarkServiceImpl implements MarkService {
    private final MarkRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final DtoConverter converter;

    @Override
    public MarkDto getCountOfLike(long postId, long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Post not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"));
        Set<Mark> likes = post.getMarks();
        long countOfLikes = likes.stream().map(Mark::getTypeOfVote).filter(t -> t.equals(TypeOfVote.Like)).count();
        long countOfDislikes = likes.stream().map(Mark::getTypeOfVote).filter(t -> t.equals(TypeOfVote.Dislike)).count();
        Mark mark = likeRepository.findByPostId(postId, userId);
        if (mark == null) {
            mark = new Mark(post, user, TypeOfVote.Nothing, new Date());
            likeRepository.save(mark);
        }
        MarkDto likeOrDislikeDto = new MarkDto(mark.getId(), postId, userId, mark.getTypeOfVote(), mark.getDate());
        likeOrDislikeDto.setCountOfLikes(countOfLikes);
        likeOrDislikeDto.setCountOfDislikes(countOfDislikes);
        return likeOrDislikeDto;
    }

    @Override
    public MarkDto saveLike(MarkDto markDto) {
        User user = userRepository.findById(markDto.getAuthorId()).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"));
        Post post = postRepository.findById(markDto.getPostId()).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Post not found"));

        Mark mark = converter.convertToMark(markDto, user, post);
        if (markDto.getId() != 0) {
            mark.setId(markDto.getId());
        }
        likeRepository.save(mark);
        Set<Mark> likes = post.getMarks();
        long countOfLikes = likes.stream().map(Mark::getTypeOfVote).filter(t -> t.equals(TypeOfVote.Like)).count();
        long countOfDislikes = likes.stream().map(Mark::getTypeOfVote).filter(t -> t.equals(TypeOfVote.Dislike)).count();
        markDto = converter.convertToMarkDto(mark);
        markDto.setCountOfDislikes(countOfDislikes);
        markDto.setCountOfLikes(countOfLikes);
        return markDto;
    }
}
