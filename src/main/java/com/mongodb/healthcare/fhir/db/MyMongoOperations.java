package com.mongodb.healthcare.fhir.db;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.stereotype.Component;

@Component
public class MyMongoOperations {

    // Logger component
    private static final Logger logger = LoggerFactory.getLogger(MyMongoOperations.class);

    @Autowired
    private MongoClientConfiguration mongoClientConfiguration;

    private MongoOperations mongoOps;

    /**
     *
     * @return
     */
    public MongoOperations getMongoOperations() {

        if(mongoOps != null) {

            logger.debug("Return existing mongoOps.");
            return mongoOps;

        } else {

            logger.debug("Create new mongoOps.");

            mongoOps =
                    new MongoTemplate(new SimpleMongoDbFactory(mongoClientConfiguration.mongoClient(),
                            mongoClientConfiguration.getDatabaseName()));
            return mongoOps;
        }

    }
}
