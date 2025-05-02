package com.oopsproject.models;

import jakarta.persistence.*;
import java.util.List;

// Using the annotation so that this class is mapped to a table in the database
@Entity
@Table(name = "article_category") // The name of the table
public class ArticleCategory {
    // This attribute is to be used as the primary key
    @Id
    @Column(name="article_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // It will have auto generated (auto incremented values)
    private Long articleCategoryId;

    // Defining the attribute categoryName which must be unique and can't be null
    @Column(name = "category_name", unique = true, nullable = false)
    private String categoryName;

    // Defining the relationship with Article entity
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Article> articles;


    // Default constructor
    public ArticleCategory() {
    }

    // Parametrized constructor
    public ArticleCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    // Getters for the field attributes
    public Long getId() {
        return articleCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<Article> getArticles() {
        return articles;
    }

    // Setters for the field attributes
    public void setId(Long id) {
        this.articleCategoryId = id;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    // Formatting the object information
    @Override
    public String toString() {
        return "ArticleCategory{" + "id=" + articleCategoryId + ", categoryName=" + categoryName + '}';
    }
}