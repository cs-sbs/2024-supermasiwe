import service.BookService;
import service.UserService;
import service.impl.BookServiceImpl;
import service.impl.UserServiceImpl;
import ui.ConsoleUI;
import util.BackupThread;

public class Main {
    public static void main(String[] args) {
        // 初始化服务
        BookService bookService = new BookServiceImpl();
        UserService userService = new UserServiceImpl();
        
        // 启动备份线程
        BackupThread backupThread = new BackupThread(bookService);
        backupThread.setDaemon(true);
        backupThread.start();
        
        // 启动控制台界面
        new ConsoleUI(bookService, userService).start();
    }
} 