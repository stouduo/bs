package com.stouduo.bs.repository;

import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import com.stouduo.bs.model.Publish;
import org.springframework.data.domain.Example;

import java.util.Optional;

public interface PublishRepository extends ArangoRepository<Publish, String> {
    @Query("for p in #collection filter p._to==@0 and p.owner==@1 return p")
    Optional<Publish> findOneByToAndOwner(String to, String owner);
}
