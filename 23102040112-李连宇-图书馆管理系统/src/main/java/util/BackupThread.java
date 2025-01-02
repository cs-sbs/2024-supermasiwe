package util;

import entity.Book;
import entity.ComputerBook;
import entity.LiteratureBook;
import entity.MedicalBook;
import service.BookService;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class BackupThread extends Thread {
    private final BookService bookService;
    private static final String BACKUP_PATH = "backup/books.csv";
    
    public BackupThread(BookService bookService) {
        this.bookService = bookService;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                backup();
                Thread.sleep(24 * 60 * 60 * 1000); // 每24小时备份一次
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void backup() {
        try {
            File backupDir = new File("backup");
            if (!backupDir.exists()) {
                backupDir.mkdir();
            }
            
            List<Book> books = bookService.getAllBooks();
            try (PrintWriter writer = new PrintWriter(new FileWriter(BACKUP_PATH))) {
                // 写入CSV头
                writer.println("ISBN,Title,Author,PublishDate,Price,Category,ExtraInfo");
                
                // 写入图书数据
                for (Book book : books) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(book.getIsbn()).append(",")
                      .append(book.getTitle()).append(",")
                      .append(book.getAuthor()).append(",")
                      .append(new SimpleDateFormat("yyyy-MM-dd").format(book.getPublishDate())).append(",")
                      .append(book.getPrice()).append(",")
                      .append(book.getCategory()).append(",");
                    
                    // 添加额外信息
                    if (book instanceof ComputerBook) {
                        ComputerBook cb = (ComputerBook) book;
                        sb.append("Language:").append(cb.getProgrammingLanguage())
                          .append(";Framework:").append(cb.getFramework());
                    } else if (book instanceof MedicalBook) {
                        MedicalBook mb = (MedicalBook) book;
                        sb.append("Subject:").append(mb.getSubject())
                          .append(";Department:").append(mb.getDepartment());
                    } else if (book instanceof LiteratureBook) {
                        LiteratureBook lb = (LiteratureBook) book;
                        sb.append("Genre:").append(lb.getGenre())
                          .append(";Language:").append(lb.getLanguage());
                    }
                    
                    writer.println(sb.toString());
                }
            }
            System.out.println("数据备份完成：" + new Date());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 