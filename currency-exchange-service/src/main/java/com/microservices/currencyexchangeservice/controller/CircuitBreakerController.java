package com.microservices.currencyexchangeservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;


@RestController
public class CircuitBreakerController {


    private Logger logger= LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
   @Retry(name="sample-api",fallbackMethod = "hardcodedResponse")
    public String sampleApi(){
        logger.info("Sample Api call received");
        ResponseEntity<String> forEntity= new RestTemplate().getForEntity("https:/localhost:8808/some-dummy-url",String.class);
        return forEntity.getBody();
    }

    @GetMapping("/sample-api2")
    @CircuitBreaker(name="default",fallbackMethod = "hardcodedResponse")
    public String sampleApi2(){
        logger.info("Sample Api call received2");
        ResponseEntity<String> forEntity= new RestTemplate().getForEntity("https:/localhost:8808/some-dummy-url",String.class);
        return forEntity.getBody();
    }


    @GetMapping("/sample-api3")
    @RateLimiter(name="default")
    public String sampleApi3(){
        logger.info("Sample Api call received3");
    //    ResponseEntity<String> forEntity= new RestTemplate().getForEntity("https:/localhost:8808/some-dummy-url",String.class);
     //   return forEntity.getBody();
        return "sample-api-rate-limiter";
    }

    @GetMapping("/sample-api4")
    @Bulkhead(name="default")
    public String sampleApi4(){
        logger.info("Sample Api call received3");
        //    ResponseEntity<String> forEntity= new RestTemplate().getForEntity("https:/localhost:8808/some-dummy-url",String.class);
        //   return forEntity.getBody();
        return "sample-api-rate-limiter";
    }

    public String hardcodedResponse(Exception ex){
        return "fallback-response";
    }
}
