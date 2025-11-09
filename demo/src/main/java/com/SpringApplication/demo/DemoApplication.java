package com.SpringApplication.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.security.Key;

import static org.yaml.snakeyaml.tokens.Token.ID.Value;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
public class DemoApplication {




    public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);
	}
	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory db){

		return new MongoTransactionManager(db);
	}




}
