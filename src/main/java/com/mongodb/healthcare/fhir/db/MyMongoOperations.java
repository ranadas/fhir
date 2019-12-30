package com.mongodb.healthcare.fhir.db;


import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MyMongoOperations {

    private final MongoClientConfiguration mongoClientConfiguration;
    private MongoOperations mongoOps;

    public MyMongoOperations(MongoClientConfiguration mongoClientConfiguration) {
        this.mongoClientConfiguration = mongoClientConfiguration;
    }

    public MongoOperations getMongoOperations() {

        if (mongoOps != null) {
            log.debug("Return existing mongoOps.");
            return mongoOps;
        } else {
            log.debug("Create new mongoOps.");
            mongoOps =
                    new MongoTemplate(new SimpleMongoDbFactory(mongoClientConfiguration.mongoClient(),
                            mongoClientConfiguration.getDatabaseName()));
            return mongoOps;
        }
    }
}
