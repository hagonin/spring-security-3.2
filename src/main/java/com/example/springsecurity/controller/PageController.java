package com.example.springsecurity.controller;

import com.example.springsecurity.entity.Article;
import com.example.springsecurity.entity.UserApp;
import com.example.springsecurity.repository.ArticleRepository;
import com.example.springsecurity.repository.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PageController {
    
    @Autowired
    private UserAppRepository userAppRepository;
    
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userApp", new UserApp());
        return "register";
    }
    
    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("userApp") UserApp userApp,
                                     RedirectAttributes redirectAttributes) {
        try {
            if (userAppRepository.existsByUsername(userApp.getUsername())) {
                redirectAttributes.addFlashAttribute("error", "Username already exists!");
                return "redirect:/register";
            }
            
            if (userAppRepository.existsByEmail(userApp.getEmail())) {
                redirectAttributes.addFlashAttribute("error", "Email already exists!");
                return "redirect:/register";
            }
            
            userApp.setPassword(passwordEncoder.encode(userApp.getPassword()));
            userApp.setEnabled(true);
            
            userAppRepository.save(userApp);
            redirectAttributes.addFlashAttribute("success", "Registration successful! You can now login.");
            return "redirect:/login";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed: " + e.getMessage());
            return "redirect:/register";
        }
    }
    
    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userAppRepository.findAll());
        return "users";
    }
    
    @GetMapping("/articles")
    public String listArticles(Model model) {
        model.addAttribute("articles", articleRepository.findByPublished(true));
        return "articles";
    }
    
    @GetMapping("/articles/new")
    public String showCreateArticleForm(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("users", userAppRepository.findAll());
        return "create-article";
    }
    
    @PostMapping("/articles")
    public String createArticle(@ModelAttribute("article") Article article,
                               @RequestParam("authorId") Long authorId,
                               RedirectAttributes redirectAttributes) {
        try {
            UserApp author = userAppRepository.findById(authorId)
                    .orElseThrow(() -> new RuntimeException("Author not found"));
            
            article.setAuthor(author);
            articleRepository.save(article);
            
            redirectAttributes.addFlashAttribute("success", "Article created successfully!");
            return "redirect:/articles";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to create article: " + e.getMessage());
            return "redirect:/articles/new";
        }
    }
}