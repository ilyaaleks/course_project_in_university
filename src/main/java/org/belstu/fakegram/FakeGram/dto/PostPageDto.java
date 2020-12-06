package org.belstu.fakegram.FakeGram.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class PostPageDto {
    private List<PostDto> posts;
    private int currentPage;
    private int totalPage;
}
