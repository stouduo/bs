package com.stouduo.bs.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.stouduo.bs.model.FollowableResource;

public interface FollowableResourceRepository extends ArangoRepository<FollowableResource, String> {
}
