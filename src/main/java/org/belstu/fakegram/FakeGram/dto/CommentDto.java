package org.belstu.fakegram.FakeGram.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class CommentDto {
    private long id;
    private String text;
    private Date date;
    private long postId;
    private String userId;
    private String authorPhotoPath;
}
