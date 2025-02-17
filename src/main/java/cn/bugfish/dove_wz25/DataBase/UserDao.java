package cn.bugfish.dove_wz25.DataBase;


import cn.bugfish.dove_wz25.UserMannageSystem.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    public boolean updateUser(User user, String key, String value) {
        String sql = "UPDATE users SET " + key + " = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, value);
            ps.setInt(2, user.getId());
            DBUtil.closeConnection(conn);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserById(Integer Userid) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Userid);
            ResultSet rs = ps.executeQuery();
            DBUtil.closeConnection(conn);
            if (rs.next()) {
                return new User(rs.getInt("id"),
                        rs.getString("role"),
                        rs.getString("steam64id"),
                        rs.getString("qqnumber"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getTimestamp("created_at")
                        );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
