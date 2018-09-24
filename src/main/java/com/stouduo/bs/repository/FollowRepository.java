package com.stouduo.bs.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.stouduo.bs.model.Follow;

public interface FollowRepository extends ArangoRepository<Follow, String> {
}
