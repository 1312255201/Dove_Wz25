package cn.bugfish.dove_wz25.UserMannageSystem.Controler;

import cn.bugfish.dove_wz25.DataBase.DBUtil;
import cn.bugfish.dove_wz25.UserMannageSystem.Model.BanState;
import cn.bugfish.dove_wz25.UserMannageSystem.Model.Player;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;


@WebServlet("/EditBanServlet")
public class EditBanServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // 设置请求的编码格式为 UTF-8
        response.setContentType("application/json;charset=UTF-8"); // 设置响应的编码格式为 UTF-8
        // 解析 JSON 数据
        Gson gson = new Gson();
        BufferedReader reader = request.getReader();
        BanState banstate = null;
        try{
            banstate = gson.fromJson(reader, BanState.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE banstate SET endtime=?,reason=? WHERE id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, banstate.getEndtime());
            stmt.setString(2, banstate.getReason());
            stmt.setInt(3, banstate.getId());
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
