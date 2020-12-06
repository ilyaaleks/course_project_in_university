package org.belstu.fakegram.FakeGram.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class ClaimDto {
    private long id;
    private long postId;
    private Date date;
    private String reason;
    private String status;
    private String authorId;
}
