package cn.bugfish.dove_wz25.UserMannageSystem.Controler;

import cn.bugfish.dove_wz25.DataBase.DBUtil;
import cn.bugfish.dove_wz25.UserMannageSystem.Model.Player;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/LoadPlayerData")
public class LoadPlayerDataServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = Integer.parseInt(request.getParameter("page"));
        String searchQuery = request.getParameter("search"); // 获取搜索参数
        int pageSize = 10; // 每页显示 10 条数据
        int offset = (page - 1) * pageSize;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Player> players = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM playerdata WHERE (userid LIKE ? OR nickname LIKE ?) ORDER BY id LIMIT ? OFFSET ?";
            stmt = conn.prepareStatement(sql);
            String query = "%" + (searchQuery != null ? searchQuery : "") + "%";
            stmt.setString(1, query); // 模糊搜索 steam64id
            stmt.setString(2, query); // 模糊搜索 nickname
            stmt.setInt(3, pageSize);
            stmt.setInt(4, offset);

            rs = stmt.executeQuery();
            while (rs.next()) {
                Player player = new Player();
                player.setUserid(rs.getString("userid"));
                player.setNickname(rs.getString("nickname"));
                player.setLevel(rs.getString("level"));
                player.setChenghao(rs.getString("chenghao"));
                player.setPoint(rs.getString("point"));
                player.setCatfood(rs.getString("catfood"));
                player.setAdmin(rs.getString("admin"));
                players.add(player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(new Gson().toJson(players));
    }
}
