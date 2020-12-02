package org.belstu.fakegram.FakeGram.service;

import org.belstu.fakegram.FakeGram.dto.CommentDto;
import org.belstu.fakegram.FakeGram.dto.CommentPageDto;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentPageDto getCommentsOfPost(long postId, Pageable page);

    CommentDto saveComment(CommentDto commentDto);
}
