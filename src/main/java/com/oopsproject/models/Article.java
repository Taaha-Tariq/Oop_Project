package com.oopsproject.models;

import jakarta.persistence.*;
import java.util.Date;

// Using the annotation so that this class is mapped to a table in the database
@Entity
@Table(name = "articles") // The name of the table
public class Article {
    // This attribute is to be used as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // It will have auto generated (auto incremented values)
    private Long articleId;

    // Defining the attribute title which can't be null
    @Column(name = "title", nullable = false)
    private String title;

    // Defining the attribute content which can't be null and has a TEXT type
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    // Defining the attribute publishedDate with temporal type of DATE
    @Column(name = "published_date")
    @Temporal(TemporalType.DATE)
    private Date publishedDate;

    // Defining the attribute thumbnailUrl
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    // Defining the relationship with ArticleCategory entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private ArticleCategory category;

    // Default constructor
    public Article() {
    }

    // Parametrized constructor
    public Article(String title, String content, Date publishedDate,
                   String thumbnailUrl, ArticleCategory category) {
        this.title = title;
        this.content = content;
        this.publishedDate = publishedDate;
        this.thumbnailUrl = thumbnailUrl;
        this.category = category;
    }

    // Getters for the field attributes
    public Long getArticleId() {
        return articleId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public ArticleCategory getCategory() {
        return category;
    }

    // Setters for the field attributes
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setCategory(ArticleCategory category) {
        this.category = category;
    }

    // Formatting the object information
    @Override
    public String toString() {
        return "Article{" + "id=" + articleId + ", title=" + title + ", content=" + content +
                ", publishedDate=" + publishedDate + ", thumbnailUrl=" + thumbnailUrl +
                ", category=" + (category != null ? category.getId() : null) + '}';
    }
}