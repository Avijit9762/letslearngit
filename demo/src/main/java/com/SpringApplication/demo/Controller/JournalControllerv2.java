package com.SpringApplication.demo.Controller;

import com.SpringApplication.demo.Entity.JournalEntry;
import com.SpringApplication.demo.Entity.User;
import com.SpringApplication.demo.Service.JournalService;
import com.SpringApplication.demo.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalControllerv2 {

@Autowired
private JournalService journalService;
@Autowired
private UserService userService;

    @GetMapping("/getAlljournals")
        public ResponseEntity<?> getAllByUserName(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        User user=userService.FindByUserName(userName);
        List<JournalEntry> List = user.getJournalEntries();
        try {
            if (List != null && !List.isEmpty()) {
                return new ResponseEntity<>(List, HttpStatus.OK);
            }
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
        catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //return new ResponseEntity<>(List,HttpStatus.OK);
        }
    @PostMapping("/createJournals/{userName}")
        public ResponseEntity<JournalEntry> CreateJournal(@RequestBody JournalEntry createEntry,@PathVariable String userName){
            User user=userService.FindByUserName(userName);
        try {
            journalService.save(createEntry, userName);
            return new ResponseEntity<>(createEntry, HttpStatus.CREATED);
        }
         catch (Exception e){

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }




    }

    @DeleteMapping("/deleteJournal/{userName}/{myId}")
    public boolean updateJournal(@PathVariable ObjectId myId,@PathVariable String userName){
        journalService.delete(myId,userName);
    
        return true;
    }
    @GetMapping("/id/{myId}")
    public  JournalEntry getJournal(@PathVariable ObjectId myId){

        return journalService.findByID(myId).orElse(null);


    }
    @PutMapping("/id/{myId}")
    public  JournalEntry update(@PathVariable ObjectId myId,@RequestBody JournalEntry newEntry){
        JournalEntry objjournalEntry = journalService.findByID(myId).orElse(null);
        if(objjournalEntry!=null){
            objjournalEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().isEmpty() ?newEntry.getContent(): objjournalEntry.getContent());
            objjournalEntry.setTitle(!newEntry.getTitle().isEmpty() ?newEntry.getTitle(): objjournalEntry.getTitle());

        }
        //journalService.save(objjournalEntry);


        return  objjournalEntry;


    }
}
