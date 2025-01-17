package cn.bugfish.dove_wz25.UserMannageSystem.Controler;
import cn.bugfish.dove_wz25.DataBase.DBUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet("/BannedPlayersServlet")
public class BannedPlayersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 数据库连接配置
            conn = DBUtil.getConnection();

            // 查询封禁玩家信息
            String sql = "SELECT userid, reason, starttime, endtime FROM banstate";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // 数据容器
            List<HashMap<String, String>> bannedPlayers = new ArrayList<>();
            int count = 0;

            while (rs.next()) {
                count++;
                HashMap<String, String> player = new HashMap<>();
                player.put("userid", rs.getString("userid"));
                player.put("reason", rs.getString("reason"));
                player.put("starttime", rs.getTimestamp("starttime").toString());
                player.put("endtime", rs.getTimestamp("endtime").toString());
                bannedPlayers.add(player);
            }

            // 构造 JSON 数据
            JsonObject responseJson = new JsonObject();
            responseJson.addProperty("total", count);
            responseJson.add("players", new Gson().toJsonTree(bannedPlayers));

            // 输出 JSON 数据
            out.print(new Gson().toJson(responseJson));

        } catch (Exception e) {
            e.printStackTrace();
            JsonObject errorJson = new JsonObject();
            errorJson.addProperty("total", 0);
            errorJson.addProperty("error", "加载封禁玩家列表时发生错误。");
            out.print(new Gson().toJson(errorJson));
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
