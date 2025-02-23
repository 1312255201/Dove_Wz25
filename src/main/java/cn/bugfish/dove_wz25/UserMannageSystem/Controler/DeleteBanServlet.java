package cn.bugfish.dove_wz25.UserMannageSystem.Controler;

import cn.bugfish.dove_wz25.DataBase.DBUtil;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

@WebServlet("/DeleteBanServlet")
public class DeleteBanServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 解析前端发送的JSON数据
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }
        String jsonData = sb.toString();

        // 使用Gson解析JSON数据
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(jsonData, Map.class);

        // 数据库连接和插入操作
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM banstate WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, map.get("id"));
            int rows = stmt.executeUpdate();
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"success\": " + (rows > 0) + "}");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        } finally {
            DBUtil.closeConnection(conn);
        }
    }

}
