package com.stouduo.bs.config;

import com.arangodb.ArangoDB;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.AbstractArangoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableArangoRepositories(basePackages = {"com.stouduo.bs.repository"})
public class ArangoConfiguration extends AbstractArangoConfiguration {
    @Value("${arangodb.database:bs}")
    private String database;
    @Value("${arangodb.host:127.0.0.1}")
    private String host;
    @Value("${arangodb.port:8529}")
    private int port;
    @Value("${arangodb.user:root}")
    private String user;
    @Value("${arangodb.password}")
    private String password;

    @Override
    public ArangoDB.Builder arango() {
        return new ArangoDB.Builder().host(host, port).user(user).password(password);
    }

    @Override
    public String database() {
        return database;
    }

}