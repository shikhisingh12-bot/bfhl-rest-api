package com.example.bfhl;

import com.example.bfhl.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BfhlApplication implements CommandLineRunner {

    @Autowired
    private ApiService apiService;

    public static void main(String[] args) {
        SpringApplication.run(BfhlApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        apiService.generateWebhook();

    }
}