package com.example.springsecurity.repository;

import com.example.springsecurity.entity.Article;
import com.example.springsecurity.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    
    List<Article> findByAuthor(UserApp author);
    
    List<Article> findByPublished(Boolean published);
    
    List<Article> findByAuthorAndPublished(UserApp author, Boolean published);
    
    List<Article> findByTitleContainingIgnoreCase(String title);
}