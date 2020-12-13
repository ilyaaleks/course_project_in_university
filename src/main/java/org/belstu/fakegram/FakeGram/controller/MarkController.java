package org.belstu.fakegram.FakeGram.controller;

import lombok.AllArgsConstructor;
import org.belstu.fakegram.FakeGram.dto.MarkDto;
import org.belstu.fakegram.FakeGram.service.MarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/vote")
@AllArgsConstructor
public class MarkController {
    public MarkService markService;

    @GetMapping("/like/count/{postId}")
    public ResponseEntity<MarkDto> getCountOfLike(@PathVariable() long postId,
                                                  @RequestParam() long userId) {
        return ResponseEntity.ok(markService.getCountOfLike(postId, userId));
    }

    @PostMapping("/like")
    public ResponseEntity<MarkDto> saveLike(@RequestBody MarkDto markDto) {
        if (markDto != null) {
            return ResponseEntity.ok(markService.saveLike(markDto));
        } else {
            throw new IllegalArgumentException();
        }
    }
}
