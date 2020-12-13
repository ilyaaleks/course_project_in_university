package org.belstu.fakegram.FakeGram.service;

import org.belstu.fakegram.FakeGram.dto.MarkDto;

public interface MarkService {
    MarkDto getCountOfLike(long postId, long userId);

    MarkDto saveLike(MarkDto markDto);
}

