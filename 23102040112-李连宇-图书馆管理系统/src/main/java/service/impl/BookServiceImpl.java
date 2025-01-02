package service.impl;

import entity.Book;
import entity.ComputerBook;
import entity.LiteratureBook;
import entity.MedicalBook;
import service.BookService;
import util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class BookServiceImpl implements BookService {
    @Override
    public void addBook(Book book) {
        String sql = "INSERT INTO books (isbn, title, author, publish_date, price, category, extra_info) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            JSONObject extraInfo = new JSONObject();
            if (book instanceof ComputerBook) {
                ComputerBook cb = (ComputerBook) book;
                extraInfo.put("programmingLanguage", cb.getProgrammingLanguage());
                extraInfo.put("framework", cb.getFramework());
            } else if (book instanceof MedicalBook) {
                MedicalBook mb = (MedicalBook) book;
                extraInfo.put("subject", mb.getSubject());
                extraInfo.put("department", mb.getDepartment());
            } else if (book instanceof LiteratureBook) {
                LiteratureBook lb = (LiteratureBook) book;
                extraInfo.put("genre", lb.getGenre());
                extraInfo.put("language", lb.getLanguage());
            }
            
            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setDate(4, new java.sql.Date(book.getPublishDate().getTime()));
            pstmt.setDouble(5, book.getPrice());
            pstmt.setString(6, book.getCategory());
            pstmt.setString(7, extraInfo.toString());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBook(String isbn) {
        String sql = "DELETE FROM books WHERE isbn = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        String sql = "SELECT * FROM books WHERE isbn = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return createBookFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> findBooksByTitle(String title) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + title + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                books.add(createBookFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> findBooksByCategory(String category) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE category = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                books.add(createBookFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                books.add(createBookFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    private Book createBookFromResultSet(ResultSet rs) throws SQLException {
        String category = rs.getString("category");
        String isbn = rs.getString("isbn");
        String title = rs.getString("title");
        String author = rs.getString("author");
        Date publishDate = rs.getDate("publish_date");
        double price = rs.getDouble("price");
        String extraInfo = rs.getString("extra_info");
        JSONObject json = extraInfo != null ? new JSONObject(extraInfo) : new JSONObject();
        
        switch (category) {
            case "计算机":
                return new ComputerBook(
                    isbn, title, author, publishDate, price,
                    json.optString("programmingLanguage", ""),
                    json.optString("framework", "")
                );
            case "医学":
                return new MedicalBook(
                    isbn, title, author, publishDate, price,
                    json.optString("subject", ""),
                    json.optString("department", "")
                );
            case "文学":
                return new LiteratureBook(
                    isbn, title, author, publishDate, price,
                    json.optString("genre", ""),
                    json.optString("language", "")
                );
            default:
                throw new SQLException("未知的图书类型：" + category);
        }
    }
} 