package com.SpringApplication.demo;

import com.SpringApplication.demo.Entity.User;
import com.SpringApplication.demo.Repository.UserRepoimpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
@SpringBootTest
public class UserRepoimpltests {

@Autowired
    private UserRepoimpl userRepoimpl;
    @Test
    public void testuser(){

        List<User> user = userRepoimpl.getUser();

    }
}
