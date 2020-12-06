package org.belstu.fakegram.FakeGram.dto;

import lombok.Builder;
import lombok.Data;
import org.belstu.fakegram.FakeGram.domain.TypeOfVote;

import java.util.Date;

@Builder
@Data
public class MarkDto {
    private long id;
    private long postId;
    private String authorId;
    private TypeOfVote typeOfVote;
    private Date date;
    private int countOfLikes;
    private int countOfDislikes;
}
