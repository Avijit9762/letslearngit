package com.SpringApplication.demo.Service;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Async
public class EmailServiceImpl {

    @Autowired
    public JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private  String fromAddress;

    private static Logger log= LoggerFactory.getLogger(EmailServiceImpl.class);

    public void SendMail(String body ,String to , String sub,File
                         Fl){
    try {
    log.info("inside SendMail  method");
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true); // 'true' = multipart
     String definedBody="Hi there Please find the PDF";
    helper.setTo(to);
    helper.setFrom(fromAddress);
    helper.setSubject(sub);
    helper.setText(definedBody);
        helper.addAttachment("Weather.json" ,Fl);
    /*FileSystemResource file = new FileSystemResource(new File("C:\\Users\\aviji\\OneDrive\\Desktop\\CV\\Avijit_Hait.pdf"));
    helper.addAttachment(file.getFilename(), file);*/
//    SimpleMailMessage mail = new SimpleMailMessage();
//    mail.setTo(to);
//    mail.setSubject(sub);
//    mail.setText(body);

    javaMailSender.send(message);
}catch(Exception Ex){
        log.error("inside SendMail  method{}", Ex.getMessage());

}


    }



}
