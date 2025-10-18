package com.SpringApplication.demo.Repository;

import com.SpringApplication.demo.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.SpringApplication.demo.Repository.UserRepository;

@Component
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
       User user = userRepository.findByUserName(userName);
       if(user!=null){
           UserDetails userDetails  = org.springframework.security.core.userdetails.User.builder().
                   username(user.getUserName()).password(user.getPassWord()).roles(user.getRoles().toArray(new String[0])).build();
       return userDetails;
       }
        throw new UsernameNotFoundException( "user not found"+userName);
    }
}
