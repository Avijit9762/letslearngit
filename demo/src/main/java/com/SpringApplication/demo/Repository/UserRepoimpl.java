package com.SpringApplication.demo.Repository;


import com.SpringApplication.demo.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserRepoimpl {
    @Autowired
    private MongoTemplate mongoTemplate;


    public List<User> getUser(){
        Query query= new Query();
       // Criteria criteria = new Criteria();
        query.addCriteria(Criteria.where("userName").is("Avijit123"));
      List<User> users= mongoTemplate.find(query, User.class);
     return users;
    }



}
