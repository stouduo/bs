package com.stouduo.bs.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.stouduo.bs.model.Inbox;

public interface InboxRepository extends ArangoRepository<Inbox, String> {
}
