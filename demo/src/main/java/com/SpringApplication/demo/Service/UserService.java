package com.SpringApplication.demo.Service;

import com.SpringApplication.demo.Entity.User;
import com.SpringApplication.demo.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

@Autowired
private UserRepository UserRepository;

private static final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();


public void save(User user)
{
    user.setPassWord(passwordEncoder.encode(user.getPassWord()));
    user.setRoles(List.of("USER"));
    UserRepository.save(user);
    //entry.setDate(LocalDateTime.now());
     //UserRepository.save(user);

}
    public void saveNewUser(User user)
    {
        //entry.setDate(LocalDateTime.now());


    }
    public List<User> get()
    {
       return UserRepository.findAll();

    }
    public Optional<User> findByID(ObjectId id){

        return UserRepository.findById(id);
    }
    public boolean delete(ObjectId id)
    {

        UserRepository.deleteById(id);

     return true;
    }
    public User FindByUserName(String userName)
    {

        return UserRepository.findByUserName(userName);


    }
}
