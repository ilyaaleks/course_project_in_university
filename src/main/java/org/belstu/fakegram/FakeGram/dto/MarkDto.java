package org.belstu.fakegram.FakeGram.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.belstu.fakegram.FakeGram.domain.TypeOfVote;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor

public class MarkDto {
    @NonNull
    private long id;
    @NonNull
    private long postId;
    @NonNull
    private long authorId;
    @NonNull
    private TypeOfVote typeOfVote;
    @NonNull
    private Date date;
    private long countOfLikes;
    private long countOfDislikes;
}
