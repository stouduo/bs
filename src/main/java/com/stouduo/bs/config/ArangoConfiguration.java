package com.stouduo.bs.config;

import com.arangodb.ArangoDB;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.AbstractArangoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableArangoRepositories(basePackages = { "com.stouduo.bs.repository" })
public class ArangoConfiguration extends AbstractArangoConfiguration {
    @Value("${arangodb.database:bs}")
    private String database;

    @Override
    public ArangoDB.Builder arango() {
        return new ArangoDB.Builder().host("127.0.0.1", 8529).user("root");
    }

    @Override
    public String database() {
        return database;
    }

}