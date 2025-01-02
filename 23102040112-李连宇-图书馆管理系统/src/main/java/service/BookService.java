package service;

import entity.Book;
import java.util.List;

public interface BookService {
    void addBook(Book book);
    void deleteBook(String isbn);
    Book findBookByIsbn(String isbn);
    List<Book> findBooksByTitle(String title);
    List<Book> findBooksByCategory(String category);
    List<Book> getAllBooks();
} 