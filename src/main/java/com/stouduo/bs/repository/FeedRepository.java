package com.stouduo.bs.repository;

import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.annotation.QueryOptions;
import com.arangodb.springframework.repository.ArangoRepository;
import com.stouduo.bs.model.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface FeedRepository extends ArangoRepository<Feed, String> {
    Page<Feed> findAllByAuthorEquals(String author, Pageable pageable);

    @Query("for v,e,p in 1..@n outbound @id graph 'engine'  filter p.edges[*].label all==@edge and  v.createTime>=@createTime and p.edges[*].owner all==@author  limit @n return v")
    List<Feed> findNextN(@Param("id") String id, @Param("n") int n, @Param("edge") String relation, @Param("author") String author, @Param("createTime") long createTime);
}
