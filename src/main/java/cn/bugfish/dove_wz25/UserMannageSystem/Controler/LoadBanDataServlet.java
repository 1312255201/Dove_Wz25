package cn.bugfish.dove_wz25.UserMannageSystem.Controler;

import cn.bugfish.dove_wz25.DataBase.DBUtil;
import cn.bugfish.dove_wz25.UserMannageSystem.GsonUtil;
import cn.bugfish.dove_wz25.UserMannageSystem.Model.BanState;
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

@WebServlet("/LoadBanData")
public class LoadBanDataServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = Integer.parseInt(request.getParameter("page"));
        String searchQuery = request.getParameter("search"); // 获取搜索参数
        int pageSize = 10; // 每页显示 10 条数据
        int offset = (page - 1) * pageSize;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<BanState> banstates = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM banstate WHERE (userid LIKE ?) ORDER BY id LIMIT ? OFFSET ?";
            stmt = conn.prepareStatement(sql);
            String query = "%" + (searchQuery != null ? searchQuery : "") + "%";
            stmt.setString(1, query); // 模糊搜索 steam64id
            stmt.setInt(2, pageSize);
            stmt.setInt(3, offset);

            rs = stmt.executeQuery();
            while (rs.next()) {
                BanState banState = new BanState();
                banState.setId(rs.getInt("id"));
                banState.setUserid(rs.getString("userid"));
                banState.setReason(rs.getString("reason"));
                banState.setStarttime(rs.getTimestamp("starttime"));
                banState.setEndtime(rs.getTimestamp("endtime"));
                banstates.add(banState);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
        }

        response.setContentType("application/json;charset=UTF-8");
        Gson gson = GsonUtil.createGson();  // 使用自定义的 Gson 实例
        response.getWriter().write(gson.toJson(banstates));
    }
}
