package com.stouduo.bs.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.stouduo.bs.model.Publish;

public interface PublishRepository extends ArangoRepository<Publish, String> {
}
