package org.belstu.fakegram.FakeGram.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CommentPageDto {
    private List<CommentDto> comments;
    private int currentPage;
    private int totalPage;
    private long countOfComments;
}
