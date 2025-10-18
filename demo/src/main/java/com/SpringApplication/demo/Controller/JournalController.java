package com.SpringApplication.demo.Controller;

import com.SpringApplication.demo.Entity.JournalEntry;

import org.bson.types.ObjectId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/_journal")
public class JournalController {
    private static final Logger logger= LoggerFactory.getLogger(JournalController.class);
    private Map<ObjectId,JournalEntry> journalEntryMap = new HashMap<ObjectId,JournalEntry>();

    @GetMapping("/getAllJournals")
    public List<JournalEntry> getAll(){

        return new  ArrayList<>(journalEntryMap.values());
    }

    @PostMapping("/createJournals")
    public boolean CreateJournal(@RequestBody JournalEntry createEntry){
        journalEntryMap.put(createEntry.getObjectId(),createEntry);
        return true;

    }
    @DeleteMapping("/deleteJournal/{myId}")
    public boolean updateJournal(@PathVariable ObjectId myId){
        journalEntryMap.remove(myId);

        return true;
    }
    @GetMapping("/id/{myId}")
    public  JournalEntry getJournal(@PathVariable ObjectId myId){
        return  journalEntryMap.get(myId);


    }
    @PutMapping("/id/{myId}")
    public  JournalEntry update(@PathVariable ObjectId myId,@RequestBody JournalEntry entry){
        return  journalEntryMap.put(myId,entry);


    }
}
