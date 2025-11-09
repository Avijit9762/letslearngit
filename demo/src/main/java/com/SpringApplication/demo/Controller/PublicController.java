package com.SpringApplication.demo.Controller;

import com.SpringApplication.demo.Entity.User;
import com.SpringApplication.demo.Model.SentimentData;
import com.SpringApplication.demo.Repository.UserDetailServiceImpl;
import com.SpringApplication.demo.Repository.UserRepoimpl;
import com.SpringApplication.demo.Service.EmailServiceImpl;
import com.SpringApplication.demo.Service.RedisServiceImpl;
import com.SpringApplication.demo.Service.UserService;
import com.SpringApplication.demo.Utility.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileWriter;

@RestController
public class PublicController {

    private static final Logger log = LoggerFactory.getLogger(PublicController.class);
    @Autowired
    public UserService userService;
    @Autowired
    public UserRepoimpl userRepoimpl;
    @Autowired
    public EmailServiceImpl emailService;
    @Autowired
    public RedisServiceImpl RedisServiceImple;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailServiceImpl userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;



    @GetMapping("/healthCheck")
    public String healthCheck() {
        return "ok";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassWord()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getWeather")
    public ResponseEntity<?> getWeather() {
        String rest = null;
        try {
              rest=RedisServiceImple.getData("/getWeather");
            log.info("response from redis" + rest);
            if (rest == null || rest.isEmpty()) {


                String weather = "";
                RestTemplate Rst = new RestTemplate();
                String url = UriComponentsBuilder.fromHttpUrl("https://api.weatherapi.com/v1/current.json").
                        queryParam("key", "8555c1c49401406bb54163248251809").queryParam("q", "Memari").toUriString();
                log.info(url);
                rest = Rst.getForObject(url, String.class);
                RedisServiceImple.saveData("/getWeather",rest);
            }
            log.info(" getWeather JSon {}", rest);
            File jsonFile = new File("Weather.json");
            try (FileWriter fl = new FileWriter(jsonFile)) {
                assert rest != null;
                fl.write(rest);

            }
            SentimentData sentimentData = new SentimentData();

            sentimentData.setSentiment("cfyi");
            sentimentData.setEmail("avijithait9762@gmail.com");
            String json = mapper.writeValueAsString(sentimentData);
            kafkaTemplate.send("topic_0", sentimentData.getEmail(), json);
            //emailService.SendMail(rest, "avijithait9762@gmail.com", "Resume: Avijit Hait", jsonFile);
            //List<User> user = userRepoimpl.getUser();

            return ResponseEntity.ok(rest);
        } catch (Exception e) {
            log.warn("inside getWeather exception{}", e.getMessage());
            return ResponseEntity.badRequest().build();

        }


    }

    ;


}
