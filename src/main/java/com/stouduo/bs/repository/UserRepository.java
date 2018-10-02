package com.stouduo.bs.repository;

import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.annotation.QueryOptions;
import com.arangodb.springframework.repository.ArangoRepository;
import com.stouduo.bs.model.FollowableResource;
import com.stouduo.bs.model.User;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends ArangoRepository<User, String> {
    @Query("FOR u IN 1..1 OUTBOUND @userId @@edge filter u.followersCount>=@followersCount RETURN DISTINCT u")
    List<FollowableResource> findAllVFollowers(@Param("userId") String userId, @Param("@edge") Class<?> edge, @Param("followersCount") int followersCount);
}
