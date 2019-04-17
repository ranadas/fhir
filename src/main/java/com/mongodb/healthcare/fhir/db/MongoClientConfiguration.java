package com.mongodb.healthcare.fhir.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class MongoClientConfiguration extends AbstractMongoConfiguration {

    @Value("${spring.data.mongodb.database:fhirDb}")
    private String database;

    @Value("${spring.data.mongodb.uri}")
    private MongoClientURI mongoClientURI;

    @Override
    public MongoClient mongoClient() {
        return new MongoClient(mongoClientURI);
    }

    @Override
    public String getDatabaseName() {
        return database;
    }
}
