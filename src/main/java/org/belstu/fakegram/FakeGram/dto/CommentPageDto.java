package org.belstu.fakegram.FakeGram.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CommentPageDto {
    private List<CommentDto> comments;
    private int currentPage;
    private int totalPage;
    private long countOfComments;
}
