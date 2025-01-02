package ui;

import entity.Book;
import service.BookService;
import service.UserService;
import entity.User;
import entity.ComputerBook;
import entity.MedicalBook;
import entity.LiteratureBook;

import java.util.List;
import java.util.Scanner;
import java.util.Date;

public class ConsoleUI {
    private final BookService bookService;
    private final UserService userService;
    private final Scanner scanner;
    private User currentUser;

    public ConsoleUI(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private void showLoginMenu() {
        System.out.println("\n=== 图书管理系统 ===");
        System.out.println("1. 登录");
        System.out.println("2. 注册");
        System.out.println("3. 退出");
        System.out.print("请选择：");

        int choice = scanner.nextInt();
        scanner.nextLine(); // 消费换行符

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.println("无效选择！");
        }
    }

    private void showMainMenu() {
        while (true) {
            System.out.println("\n=== 主菜单 ===");
            System.out.println("1. 查看所有图书");
            System.out.println("2. 搜索图书");
            System.out.println("3. 按分类浏览");
            if (currentUser.isAdmin()) {
                System.out.println("4. 添加图书");
                System.out.println("5. 删除图书");
            }
            System.out.println("0. 退出登录");
            System.out.print("请选择：");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 消费换行符

            switch (choice) {
                case 0:
                    currentUser = null;
                    return;
                case 1:
                    showAllBooks();
                    break;
                case 2:
                    searchBooks();
                    break;
                case 3:
                    browseByCategory();
                    break;
                case 4:
                    if (currentUser.isAdmin()) {
                        addBook();
                    } else {
                        System.out.println("无效选择！");
                    }
                    break;
                case 5:
                    if (currentUser.isAdmin()) {
                        deleteBook();
                    } else {
                        System.out.println("无效选择！");
                    }
                    break;
                default:
                    System.out.println("无效选择！");
            }
        }
    }

    private void login() {
        System.out.print("请输入用户名：");
        String username = scanner.nextLine();
        System.out.print("请输入密码：");
        String password = scanner.nextLine();

        User user = userService.login(username, password);
        if (user != null) {
            currentUser = user;
            System.out.println("登录成功！");
        } else {
            System.out.println("用户名或密码错误！");
        }
    }

    private void register() {
        System.out.print("请输入用户名：");
        String username = scanner.nextLine();
        System.out.print("请输入密码：");
        String password = scanner.nextLine();

        if (userService.register(username, password)) {
            System.out.println("注册成功！请登录。");
        } else {
            System.out.println("注册失败，用户名可能已存在。");
        }
    }

    private void showAllBooks() {
        List<Book> books = bookService.getAllBooks();
        displayBooks(books);
    }

    private void searchBooks() {
        System.out.println("\n=== 搜索图书 ===");
        System.out.println("1. 按ISBN搜索");
        System.out.println("2. 按书名搜索");
        System.out.print("请选择：");

        int choice = scanner.nextInt();
        scanner.nextLine(); // 消费换行符

        switch (choice) {
            case 1:
                searchByIsbn();
                break;
            case 2:
                searchByTitle();
                break;
            default:
                System.out.println("无效选择！");
        }
    }

    private void searchByIsbn() {
        System.out.print("请输入ISBN：");
        String isbn = scanner.nextLine();
        Book book = bookService.findBookByIsbn(isbn);
        if (book != null) {
            book.displayInfo();
        } else {
            System.out.println("未找到相关图书！");
        }
    }

    private void searchByTitle() {
        System.out.print("请输入书名关键词：");
        String title = scanner.nextLine();
        List<Book> books = bookService.findBooksByTitle(title);
        displayBooks(books);
    }

    private void browseByCategory() {
        System.out.print("请输入图书分类：");
        String category = scanner.nextLine();
        List<Book> books = bookService.findBooksByCategory(category);
        displayBooks(books);
    }

    private void displayBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("未找到相关图书！");
            return;
        }
        System.out.println("\n查找到以下图书：");
        for (Book book : books) {
            book.displayInfo();
            System.out.println("----------------");
        }
    }

    private void addBook() {
        System.out.println("\n=== 添加图书 ===");
        System.out.println("请选择图书类型：");
        System.out.println("1. 计算机类");
        System.out.println("2. 医学类");
        System.out.println("3. 文学类");
        System.out.print("请选择：");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // 消费换行符
        
        // 获取通用信息
        System.out.print("ISBN：");
        String isbn = scanner.nextLine();
        System.out.print("书名：");
        String title = scanner.nextLine();
        System.out.print("作者：");
        String author = scanner.nextLine();
        System.out.print("价格：");
        double price = scanner.nextDouble();
        scanner.nextLine(); // 消费换行符
        
        Book book = null;
        Date publishDate = new Date(); // 这里简化处理，使用当前日期
        
        switch (choice) {
            case 1:
                System.out.print("编程语言：");
                String language = scanner.nextLine();
                System.out.print("框架：");
                String framework = scanner.nextLine();
                book = new ComputerBook(isbn, title, author, publishDate, price, language, framework);
                break;
            case 2:
                System.out.print("医学科目：");
                String subject = scanner.nextLine();
                System.out.print("适用科室：");
                String department = scanner.nextLine();
                book = new MedicalBook(isbn, title, author, publishDate, price, subject, department);
                break;
            case 3:
                System.out.print("文学流派：");
                String genre = scanner.nextLine();
                System.out.print("原著语言：");
                String originalLanguage = scanner.nextLine();
                book = new LiteratureBook(isbn, title, author, publishDate, price, genre, originalLanguage);
                break;
            default:
                System.out.println("无效的图书类型！");
                return;
        }
        
        bookService.addBook(book);
        System.out.println("图书添加成功！");
    }

    private void deleteBook() {
        System.out.print("请输入要删除的图书ISBN：");
        String isbn = scanner.nextLine();
        bookService.deleteBook(isbn);
        System.out.println("图书已删除！");
    }
} 