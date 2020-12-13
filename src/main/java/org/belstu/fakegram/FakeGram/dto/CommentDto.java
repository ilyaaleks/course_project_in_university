package org.belstu.fakegram.FakeGram.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class CommentDto {
    private long id;
    @NonNull
    private String text;
    @NonNull
    private Date date;
    @NonNull
    private long postId;
    @NonNull
    private long userId;
    @NonNull
    private String authorPhotoPath;
}
