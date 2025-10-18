package com.SpringApplication.demo.Entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Document(collection = "journal_entries")
@NoArgsConstructor
public class
JournalEntry {
    @Id
    private ObjectId objectId;
    @NonNull
    private String  title;
    private String  content;
    private LocalDateTime date;



}
