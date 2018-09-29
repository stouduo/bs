package com.stouduo.bs.repository;

import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import com.stouduo.bs.model.Follow;
import com.stouduo.bs.model.FollowableResource;
import com.stouduo.bs.model.User;
import org.springframework.data.domain.Example;

import java.util.Optional;

public interface FollowRepository extends ArangoRepository<Follow, String> {
    @Query("for follow in #collection filter follow._from ==@0 and follow._to==@1 return follow")
    Optional<Follow> findOneByFromAndTo(String from, String to);

    @Query("for follow in #collection filter follow._to==@0 return follow")
    Iterable<Follow> findAll(String to);
}
