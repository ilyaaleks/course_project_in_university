package org.belstu.fakegram.FakeGram.controller;

import lombok.AllArgsConstructor;
import org.belstu.fakegram.FakeGram.dto.CommentDto;
import org.belstu.fakegram.FakeGram.dto.CommentPageDto;
import org.belstu.fakegram.FakeGram.service.CommentService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/comment")
@AllArgsConstructor
public class CommentController {
    private CommentService commentService;
    @GetMapping("/{postId}")
    public ResponseEntity<CommentPageDto> getCommentsOfPost(@PathVariable() long postId,
                                                            Pageable page) {
        return ResponseEntity.ok(commentService.getCommentsOfPost(postId,page));
    }

    @PostMapping
    public ResponseEntity<CommentDto> saveComment(@RequestBody CommentDto commentDto) {
        if (commentDto != null) {
            return ResponseEntity.ok(commentService.saveComment(commentDto));
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }
}
