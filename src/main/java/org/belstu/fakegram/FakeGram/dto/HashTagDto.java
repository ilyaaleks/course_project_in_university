package org.belstu.fakegram.FakeGram.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HashTagDto {
    private long id;
    private String text;
}
