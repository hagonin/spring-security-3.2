package com.example.springsecurity.controller;

import com.example.springsecurity.entity.Article;
import com.example.springsecurity.entity.UserApp;
import com.example.springsecurity.repository.ArticleRepository;
import com.example.springsecurity.repository.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private UserAppRepository userAppRepository;
    
    @GetMapping
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }
    
    @GetMapping("/published")
    public List<Article> getPublishedArticles() {
        return articleRepository.findByPublished(true);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        Optional<Article> article = articleRepository.findById(id);
        return article.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Article>> getArticlesByAuthor(@PathVariable Long authorId) {
        Optional<UserApp> author = userAppRepository.findById(authorId);
        if (author.isPresent()) {
            List<Article> articles = articleRepository.findByAuthor(author.get());
            return ResponseEntity.ok(articles);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<String> createArticle(@RequestBody ArticleRequest request) {
        try {
            Optional<UserApp> author = userAppRepository.findById(request.getAuthorId());
            if (author.isEmpty()) {
                return ResponseEntity.badRequest().body("Author not found");
            }
            
            Article article = new Article();
            article.setTitle(request.getTitle());
            article.setContent(request.getContent());
            article.setAuthor(author.get());
            article.setPublished(request.getPublished() != null ? request.getPublished() : false);
            
            articleRepository.save(article);
            return ResponseEntity.ok("Article created successfully");
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create article: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody ArticleRequest request) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            article.setTitle(request.getTitle());
            article.setContent(request.getContent());
            if (request.getPublished() != null) {
                article.setPublished(request.getPublished());
            }
            return ResponseEntity.ok(articleRepository.save(article));
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        if (articleRepository.existsById(id)) {
            articleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    public static class ArticleRequest {
        private String title;
        private String content;
        private Long authorId;
        private Boolean published;
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        
        public Long getAuthorId() { return authorId; }
        public void setAuthorId(Long authorId) { this.authorId = authorId; }
        
        public Boolean getPublished() { return published; }
        public void setPublished(Boolean published) { this.published = published; }
    }
}