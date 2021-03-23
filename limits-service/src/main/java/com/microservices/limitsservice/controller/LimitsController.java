package com.microservices.limitsservice.controller;

import com.microservices.limitsservice.config.Configuration;
import com.microservices.limitsservice.model.Limits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {

    @Autowired
    private Configuration configuration;

    @GetMapping("/limits")
    public Limits retriveLimits(){
        return new Limits(configuration.getMinimum(),configuration.getMaximum());
    }

    /*@GetMapping("/limits")
    public Limits retriveLimits(){
        return new Limits(1,1000);
    }*/
}
