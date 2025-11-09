package com.SpringApplication.demo.Controller;

import com.SpringApplication.demo.Entity.JournalEntry;
import com.SpringApplication.demo.Entity.User;
import com.SpringApplication.demo.Service.JournalService;
import com.SpringApplication.demo.Service.UserService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;
    @Autowired
    private JournalService journalService;

    @GetMapping("/getAllUsers")
    public List<User> getAllUser() {

        return userService.get();
    }

    @PostMapping("/createUser")
    public User CreateUser(@RequestBody User user) {
        log.info("inside Create user method");
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        userService.save(user);

        return user;
    }


    @DeleteMapping("/deleteUSer/{myId}")
    public boolean deleteUser(@PathVariable ObjectId myId) {
        userService.delete(myId);
        return true;
    }


    @GetMapping("/id/{myId}")
    public User getUserById(@PathVariable ObjectId myId) {

        return userService.findByID(myId).orElse(null);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User oldUser = userService.FindByUserName(userName);
        List<JournalEntry> List = journalService.get();
        if (oldUser != null) {
            oldUser.setUserName(!user.getUserName().isEmpty() ? user.getUserName() : oldUser.getUserName());
            oldUser.setPassWord(!user.getPassWord().isEmpty() ? user.getPassWord() : oldUser.getPassWord());

        }
        assert oldUser != null;
        userService.save(oldUser);


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
