package service.impl;

import entity.User;
import service.UserService;
import util.DatabaseUtil;
import java.sql.*;

public class UserServiceImpl implements UserService {
    @Override
    public boolean register(String username, String password) {
        String sql = "INSERT INTO users (username, password, is_admin) VALUES (?, ?, false)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getBoolean("is_admin")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
} 