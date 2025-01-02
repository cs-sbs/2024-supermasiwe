package entity;

import java.util.Date;

public class ComputerBook extends Book {
    private String programmingLanguage;
    private String framework;
    
    public ComputerBook(String isbn, String title, String author, Date publishDate, 
                       double price, String programmingLanguage, String framework) {
        super(isbn, title, author, publishDate, price, "计算机");
        this.programmingLanguage = programmingLanguage;
        this.framework = framework;
    }
    
    @Override
    public void displayInfo() {
        System.out.println("计算机类图书信息：");
        System.out.println("ISBN：" + getIsbn());
        System.out.println("书名：" + getTitle());
        System.out.println("作者：" + getAuthor());
        System.out.println("编程语言：" + programmingLanguage);
        System.out.println("框架：" + framework);
    }
    
    public String getProgrammingLanguage() { return programmingLanguage; }
    public String getFramework() { return framework; }
} 