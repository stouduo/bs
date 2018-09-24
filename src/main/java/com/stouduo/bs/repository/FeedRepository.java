package com.stouduo.bs.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.stouduo.bs.model.Feed;

public interface FeedRepository extends ArangoRepository<Feed, String> {
}
