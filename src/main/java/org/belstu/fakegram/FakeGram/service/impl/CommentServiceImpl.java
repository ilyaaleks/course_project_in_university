package org.belstu.fakegram.FakeGram.service.impl;

import lombok.AllArgsConstructor;
import org.belstu.fakegram.FakeGram.domain.Comment;
import org.belstu.fakegram.FakeGram.domain.Post;
import org.belstu.fakegram.FakeGram.domain.User;
import org.belstu.fakegram.FakeGram.dto.CommentDto;
import org.belstu.fakegram.FakeGram.dto.CommentPageDto;
import org.belstu.fakegram.FakeGram.mapper.DtoConverter;
import org.belstu.fakegram.FakeGram.repository.CommentRepository;
import org.belstu.fakegram.FakeGram.service.CommentService;
import org.belstu.fakegram.FakeGram.service.PostService;
import org.belstu.fakegram.FakeGram.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    public final UserService userService;
    public final PostService postService;
    public final DtoConverter converter;
    @Override
    @Transactional
    public CommentPageDto getCommentsOfPost(long postId, Pageable pageable) {
        final Page<Comment> page = commentRepository.findByPostId(postId, pageable);
        final List<Comment> commentList = page.getContent();
        final List<CommentDto> commentDtoList = commentList.stream().map(converter::convertToCommentDto).collect(Collectors.toList());
        return new CommentPageDto(commentDtoList,
                pageable.getPageNumber(),
                page.getTotalPages(),
                page.getTotalElements());
    }

    @Override
    @Transactional
    public CommentDto saveComment(CommentDto commentDto) {
        User user = userService.findById(commentDto.getUserId());
        Post post =postService.findById(commentDto.getPostId());
        Comment comment = converter.convertToComment(commentDto, user, post);
        comment.setDate(new Date());
        comment = commentRepository.save(comment);
        return converter.convertToCommentDto(comment);
    }
}
