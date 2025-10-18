package com.SpringApplication.demo.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class MyClass {
    @Autowired
   private Utility utility;
    @GetMapping("/dog")
    public String method(){
        return utility.bark();
    }

}
