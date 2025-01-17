package cn.bugfish.dove_wz25.UserMannageSystem.Controler;

import cn.bugfish.dove_wz25.DataBase.DBUtil;
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


@WebServlet("/EditPlayerServlet")
public class EditPlayerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // 设置请求的编码格式为 UTF-8
        response.setContentType("application/json;charset=UTF-8"); // 设置响应的编码格式为 UTF-8
        // 解析 JSON 数据
        Gson gson = new Gson();
        BufferedReader reader = request.getReader();
        Player playerData = null;
        try{
            playerData = gson.fromJson(reader, Player.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE playerdata SET point=?, catfood=?, catfoodmutiply=?, exp=?, expmutiply=?, level=?, mvpmusic=?, chenghao=?, chenghaocolor=?, admin=?, overtime=?, manrenjinfu=?, jishayinxiao=?,jinfuguangbo=?, youxian=? WHERE id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, playerData.getPoint());
            stmt.setInt(2, playerData.getCatfood());
            stmt.setInt(3, playerData.getCatfoodmutiply());
            stmt.setInt(4, playerData.getExp());
            stmt.setInt(5, playerData.getExpmutiply());
            stmt.setInt(6, playerData.getLevel());
            stmt.setString(7, playerData.getMvpmusic());
            stmt.setString(8, playerData.getChenghao());
            stmt.setString(9, playerData.getChenghaocolor());
            stmt.setString(10, playerData.getAdmin());
            stmt.setTimestamp(11, playerData.getOvertime());
            stmt.setString(12, playerData.getManrenjinfu());
            stmt.setString(13, playerData.getJishayinxiao());
            stmt.setString(14, playerData.getJinfuguangbo());
            stmt.setString(15, playerData.getYouxian());
            stmt.setInt(16, playerData.getId());

            int rows = stmt.executeUpdate();
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"success\": " + (rows > 0) + "}");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        } finally {
        }
    }
}
