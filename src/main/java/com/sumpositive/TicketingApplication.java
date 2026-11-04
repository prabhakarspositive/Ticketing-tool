package com.sumpositive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;

@SpringBootApplication
public class TicketingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingApplication.class, args);
    }

}
