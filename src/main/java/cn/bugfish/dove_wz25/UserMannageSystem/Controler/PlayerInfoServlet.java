package cn.bugfish.dove_wz25.UserMannageSystem.Controler;

import cn.bugfish.dove_wz25.DataBase.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/PlayerInfoServlet")
public class PlayerInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应的内容类型为 HTML
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 从 Session 获取 steam64id
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usersteam64id") == null) {
            out.println("<p>未登录或会话已过期。</p>");
            return;
        }

        String steam64id = (String) session.getAttribute("usersteam64id");

        // 数据库查询
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {

            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM playerdata WHERE userid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, steam64id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // 输出玩家信息
                out.println("<table>");
                out.println("<tr><th>信息类型</th><th>内容</th></tr>");
                out.println("<tr><td>Steam64ID</td><td>" + rs.getString("userid") + "</td></tr>");
                out.println("<tr><td>游戏名称</td><td>" + rs.getString("nickname") + "</td></tr>");
                out.println("<tr><td>积分</td><td>" + rs.getInt("point") + "</td></tr>");
                out.println("<tr><td>猫粮</td><td>" + rs.getInt("catfood") + "</td></tr>");
                out.println("<tr><td>经验值</td><td>" + rs.getInt("exp") + "</td></tr>");
                out.println("<tr><td>经验值倍数</td><td>" + rs.getInt("expmutiply") + "</td></tr>");
                out.println("<tr><td>等级</td><td>" + rs.getInt("level") + "</td></tr>");
                out.println("<tr><td>总击杀数</td><td>" + rs.getInt("killnum") + "</td></tr>");
                out.println("<tr><td>MVP次数</td><td>" + rs.getInt("mvptime") + "</td></tr>");
                out.println("<tr><td>MVP音乐盒:</td><td>" + ("0".equals(rs.getString("mvpmusic")) ? "无" : rs.getString("mvpmusic")) + "</td></tr>");
                out.println("<tr><td>称号</td><td>" + rs.getString("chenghao") + "</td></tr>");
                out.println("<tr><td>称号颜色</td><td>" + rs.getString("chenghaocolor") + "</td></tr>");
                out.println("<tr><td>身份组信息</td><td>" + ("0".equals(rs.getString("admin")) ? "玩家" : (rs.getString("admin") + "到期时间" +rs.getTimestamp("overtime")))+ "</td></tr>");
                out.println("<tr><td>满人进服通道</td><td>" + ("0".equals(rs.getString("manrenjinfu")) ? "无" : "有")+ "</td></tr>");
                out.println("<tr><td>进服广播</td><td>" + ("0".equals(rs.getString("jinfuguangbo")) ? "无" : rs.getString("jinfuguangbo"))+ "</td></tr>");
                out.println("<tr><td>优先</td><td>" + ("0".equals(rs.getString("youxian")) ? "无" : "有")+ "</td></tr>");
                out.println("</table>");
            } else {
                out.println("<p>未找到玩家信息。</p>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>加载玩家信息时发生错误，请联系网站管理员。</p>");
        } finally {
            // 关闭资源
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
