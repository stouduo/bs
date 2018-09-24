package com.stouduo.bs.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.stouduo.bs.model.Link;

public interface LinkRepository extends ArangoRepository<Link, String> {
}
