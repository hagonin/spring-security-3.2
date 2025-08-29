package com.example.springsecurity.controller;

import com.example.springsecurity.entity.UserApp;
import com.example.springsecurity.repository.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserAppController {
    
    @Autowired
    private UserAppRepository userAppRepository;
    
    @GetMapping
    public List<UserApp> getAllUsers() {
        return userAppRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserApp> getUserById(@PathVariable Long id) {
        Optional<UserApp> user = userAppRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/username/{username}")
    public ResponseEntity<UserApp> getUserByUsername(@PathVariable String username) {
        Optional<UserApp> user = userAppRepository.findByUsername(username);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public UserApp createUser(@RequestBody UserApp userApp) {
        return userAppRepository.save(userApp);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserApp> updateUser(@PathVariable Long id, @RequestBody UserApp userDetails) {
        Optional<UserApp> optionalUser = userAppRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserApp user = optionalUser.get();
            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            user.setEmail(userDetails.getEmail());
            user.setFirstName(userDetails.getFirstName());
            user.setLastName(userDetails.getLastName());
            user.setEnabled(userDetails.getEnabled());
            return ResponseEntity.ok(userAppRepository.save(user));
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userAppRepository.existsById(id)) {
            userAppRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}