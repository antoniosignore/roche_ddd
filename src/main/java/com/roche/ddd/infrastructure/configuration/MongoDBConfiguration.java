package com.roche.ddd.infrastructure.configuration;

import com.roche.ddd.infrastructure.repository.mongo.SpringDataMongoOrderRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = SpringDataMongoOrderRepository.class)
public class MongoDBConfiguration {
}
