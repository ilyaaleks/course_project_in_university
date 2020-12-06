package org.belstu.fakegram.FakeGram.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Builder
@Data
public class PostDto {
    private long id;
    private String authorId;
    private String authorLogin;
    private String photoPath;
    private String text;
    private Date date;
    private String authorPhotoPath;
    private Set<HashTagDto> hashTags;
}
