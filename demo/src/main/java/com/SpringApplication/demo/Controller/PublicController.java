package com.SpringApplication.demo.Controller;

import com.SpringApplication.demo.Entity.User;
import com.SpringApplication.demo.Repository.UserRepoimpl;
import com.SpringApplication.demo.Service.EmailServiceImpl;
import com.SpringApplication.demo.Service.RedisServiceImple;
import com.SpringApplication.demo.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

@RestController
public class PublicController {

  private static final Logger log= LoggerFactory.getLogger(PublicController.class);
  @Autowired
  public UserService userService;
  @Autowired
  public UserRepoimpl userRepoimpl;
  @Autowired
  public EmailServiceImpl emailService;
  @Autowired
  public RedisServiceImple RedisServiceImple;



  @GetMapping("/healthCheck")
    public String healthCheck(){
      return"ok";
    }

    @GetMapping("/getWeather")
  public  ResponseEntity<?> getWeather(){
      String rest = null;
      try {
          rest=RedisServiceImple.getData("/getWeather");
          log.info("response from redis"+rest);
          if(rest == null || rest.isEmpty()){


           String weather="";
           RestTemplate Rst = new RestTemplate();
           String url= UriComponentsBuilder.fromHttpUrl( "https://api.weatherapi.com/v1/current.json").
           queryParam("key","8555c1c49401406bb54163248251809").queryParam("q","Memari").toUriString();
           log.info(url);
           rest = Rst.getForObject(url, String.class);
              RedisServiceImple.saveData("/getWeather",rest);
          }
          log.info(" getWeather JSon {}", rest);
           File jsonFile= new File("Weather.json");
           try(FileWriter fl = new FileWriter(jsonFile)){
               assert rest != null;
               fl.write(rest);

           }

          emailService.SendMail(rest,"avijithait9762@gmail.com","Resume: Avijit Hait",jsonFile);
          //List<User> user = userRepoimpl.getUser();

          return ResponseEntity.ok(rest);
        }
        catch(Exception e){
            log.warn("inside getWeather exception{}", e.getMessage());
          return ResponseEntity.badRequest().build();

        }



    };


}
