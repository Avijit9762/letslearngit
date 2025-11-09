package com.SpringApplication.demo.Service;

import com.SpringApplication.demo.Model.SentimentData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerService {
    private static final Logger log = LoggerFactory.getLogger(SentimentConsumerService.class);


    @Autowired
    private EmailServiceImpl emailService;

  /*  @KafkaListener(topics = "topic_0", groupId = "my-group")
    public void consume(SentimentData sentimentData) {
        try {
            sendEmail(sentimentData);
        }
        catch(Exception Ex){
            log.info("inside SendMail  method"+Ex.getMessage());

        }

    }*/

    private void sendEmail(SentimentData sentimentData) {
        try {
            emailService.SendEmail( "Sentiment for previous week",sentimentData.getEmail(), sentimentData.getSentiment());
        }
        catch(Exception Ex){
            log.info("inside SendMail  method"+Ex.getMessage());

        }
    }
}