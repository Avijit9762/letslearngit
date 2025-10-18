package com.SpringApplication.demo.Service;

import com.SpringApplication.demo.Entity.JournalEntry;
import com.SpringApplication.demo.Entity.User;
import com.SpringApplication.demo.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalService {

@Autowired
private JournalEntryRepository journalEntryRepository;
@Autowired
private UserService userService;


@Transactional
public void save(JournalEntry entry,String UserName)
{
    try{
    User user = userService.FindByUserName(UserName);

    entry.setDate(LocalDateTime.now());

    JournalEntry saved = journalEntryRepository.save(entry);
    user.getJournalEntries().add(saved);
   //user.setId(null);
    userService.save(user);
        }
catch(Exception Ex){
    System.out.println(Ex.toString());
}


}
    public List<JournalEntry> get()
    {
       return journalEntryRepository.findAll();

    }
    public Optional<JournalEntry> findByID(ObjectId id){

    return journalEntryRepository.findById(id);
    }
    public boolean delete(ObjectId id, String userName)
    {
        User user = userService.FindByUserName(userName);
        user.getJournalEntries().removeIf(x-> x.getObjectId().equals(id));
        userService.save(user);
       journalEntryRepository.deleteById(id);

     return true;
    }
}
