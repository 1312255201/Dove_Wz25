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

@WebServlet("/BanPlayerServlet")
public class BanPlayerServlet extends HttpServlet {
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
        BanRequest banRequest = gson.fromJson(jsonData, BanRequest.class);

        // 数据库连接和插入操作
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO banstate (userid, reason, starttime, endtime) VALUES (?, ?, NOW(), ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, banRequest.getUserid());
            stmt.setString(2, banRequest.getReason());
            stmt.setString(3, banRequest.getEndtime());

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

// 用于接收封禁请求的类
class BanRequest {
    private String userid;
    private String reason;
    private String endtime;  // 前端传来的结束时间

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
