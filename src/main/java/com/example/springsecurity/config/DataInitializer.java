package com.example.springsecurity.config;

import com.example.springsecurity.entity.UserApp;
import com.example.springsecurity.repository.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserAppRepository userAppRepository;
    
    @Override
    public void run(String... args) throws Exception {
        if (userAppRepository.count() == 0) {
            System.out.println("Initializing database with sample users...");
            
            UserApp user1 = new UserApp();
            user1.setUsername("john_doe");
            user1.setPassword("password123");
            user1.setEmail("john.doe@example.com");
            user1.setFirstName("John");
            user1.setLastName("Doe");
            user1.setEnabled(true);
            
            UserApp user2 = new UserApp();
            user2.setUsername("jane_smith");
            user2.setPassword("password456");
            user2.setEmail("jane.smith@example.com");
            user2.setFirstName("Jane");
            user2.setLastName("Smith");
            user2.setEnabled(true);
            
            UserApp user3 = new UserApp();
            user3.setUsername("bob_wilson");
            user3.setPassword("password789");
            user3.setEmail("bob.wilson@example.com");
            user3.setFirstName("Bob");
            user3.setLastName("Wilson");
            user3.setEnabled(false);
            
            userAppRepository.save(user1);
            userAppRepository.save(user2);
            userAppRepository.save(user3);
            
            System.out.println("Sample users added successfully!");
        } else {
            System.out.println("Users already exist in the database.");
        }
    }
}