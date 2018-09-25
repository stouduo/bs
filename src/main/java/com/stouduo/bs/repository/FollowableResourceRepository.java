package com.stouduo.bs.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.stouduo.bs.model.FollowableResource;
import com.stouduo.bs.model.Link;

public interface FollowableResourceRepository extends ArangoRepository<FollowableResource, String> {
}
