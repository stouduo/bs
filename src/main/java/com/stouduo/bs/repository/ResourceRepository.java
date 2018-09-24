package com.stouduo.bs.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.stouduo.bs.model.Resource;

public interface ResourceRepository extends ArangoRepository<Resource, String> {
}
