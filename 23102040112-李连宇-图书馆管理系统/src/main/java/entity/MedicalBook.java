package entity;

import java.util.Date;

public class MedicalBook extends Book {
    private String subject;     // 医学科目
    private String department;  // 适用科室
    
    public MedicalBook(String isbn, String title, String author, Date publishDate, 
                      double price, String subject, String department) {
        super(isbn, title, author, publishDate, price, "医学");
        this.subject = subject;
        this.department = department;
    }
    
    @Override
    public void displayInfo() {
        System.out.println("医学类图书信息：");
        System.out.println("ISBN：" + getIsbn());
        System.out.println("书名：" + getTitle());
        System.out.println("作者：" + getAuthor());
        System.out.println("出版日期：" + getPublishDate());
        System.out.println("价格：" + getPrice());
        System.out.println("医学科目：" + subject);
        System.out.println("适用科室：" + department);
    }
    
    public String getSubject() { return subject; }
    public String getDepartment() { return department; }
} 