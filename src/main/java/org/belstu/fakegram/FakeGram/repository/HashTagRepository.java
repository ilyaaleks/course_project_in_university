package org.belstu.fakegram.FakeGram.repository;

import org.belstu.fakegram.FakeGram.domain.HashTag;
import org.springframework.data.repository.CrudRepository;

public interface HashTagRepository extends CrudRepository<HashTag, Long> {
    HashTag findByText(String hashTag);
}
