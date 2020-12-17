package org.belstu.fakegram.FakeGram.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class PostDto {
    private long id;
    @NonNull
    private long authorId;
    @NonNull
    private String authorLogin;
    @NonNull
    private String photoPath;
    @NonNull
    private String text;
    @NonNull
    private Date date;
    private String authorPhotoPath;
    private Set<HashTagDto> hashTags;
}
