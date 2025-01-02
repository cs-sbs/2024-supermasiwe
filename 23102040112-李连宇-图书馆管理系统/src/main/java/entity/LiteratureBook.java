package entity;

import java.util.Date;

public class LiteratureBook extends Book {
    private String genre;      // 文学流派
    private String language;   // 原著语言
    
    public LiteratureBook(String isbn, String title, String author, Date publishDate, 
                         double price, String genre, String language) {
        super(isbn, title, author, publishDate, price, "文学");
        this.genre = genre;
        this.language = language;
    }
    
    @Override
    public void displayInfo() {
        System.out.println("文学类图书信息：");
        System.out.println("ISBN：" + getIsbn());
        System.out.println("书名：" + getTitle());
        System.out.println("作者：" + getAuthor());
        System.out.println("出版日期：" + getPublishDate());
        System.out.println("价格：" + getPrice());
        System.out.println("文学流派：" + genre);
        System.out.println("原著语言：" + language);
    }
    
    public String getGenre() { return genre; }
    public String getLanguage() { return language; }
} 