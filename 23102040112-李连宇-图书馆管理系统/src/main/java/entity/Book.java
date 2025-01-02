package entity;

import java.util.Date;

public abstract class Book {
    private String isbn;
    private String title;
    private String author;
    private Date publishDate;
    private double price;
    private String category;
    
    // 构造函数
    public Book(String isbn, String title, String author, Date publishDate, double price, String category) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
        this.price = price;
        this.category = category;
    }
    
    // Getter方法
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public Date getPublishDate() { return publishDate; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    
    // Setter方法
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setPublishDate(Date publishDate) { this.publishDate = publishDate; }
    public void setPrice(double price) { this.price = price; }
    public void setCategory(String category) { this.category = category; }
    
    // 抽象方法，用于多态展示图书信息
    public abstract void displayInfo();
} 