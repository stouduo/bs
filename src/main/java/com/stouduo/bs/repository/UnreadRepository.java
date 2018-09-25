package com.stouduo.bs.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.stouduo.bs.model.Unread;

public interface UnreadRepository extends ArangoRepository<Unread, String> {
}
