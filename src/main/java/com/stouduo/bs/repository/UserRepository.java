package com.stouduo.bs.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.stouduo.bs.model.User;

public interface UserRepository extends ArangoRepository<User, String> {
}
