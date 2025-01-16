package cn.bugfish.dove_wz25.UserMannageSystem.Controler;

import cn.bugfish.dove_wz25.DataBase.DBUtil;
import cn.bugfish.dove_wz25.UserMannageSystem.Loger.UserMannageSystemLoger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * 处理 HTTP POST 请求，用于验证用户登录信息
     *
     * @param request  包含客户端请求信息的 HttpServletRequest 对象
     * @param response 包含服务器响应信息的 HttpServletResponse 对象
     * @throws ServletException 如果在处理请求过程中发生异常
     * @throws IOException      如果在处理请求或响应时发生 I/O 错误
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从请求参数中获取用户名
        String username = request.getParameter("username");
        // 从请求参数中获取密码
        String password = request.getParameter("password");
        // 从请求参数中获取验证码

        String captcha = request.getParameter("captcha");

        // 验证输入是否为空
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                captcha == null || captcha.trim().isEmpty()) {
            // 如果输入为空，重定向到登录页面并显示错误信息
            response.sendRedirect("login.jsp?error=emptyFields");
            return;
        }

        // 验证验证码
        HttpSession session = request.getSession();
        // 从会话中获取验证码
        String sessionCaptcha = (String) session.getAttribute("captcha");
        if (sessionCaptcha == null || !sessionCaptcha.equalsIgnoreCase(captcha)) {
            // 如果验证码无效，重定向到登录页面并显示错误信息
            response.sendRedirect("login.jsp?error=invalidCaptcha");
            return;
        }

        Connection conn = null;
        try {
            // 获取数据库连接
            conn = DBUtil.getConnection();
            String query;
            // 根据用户名判断查询条件
            if (username.contains("@steam")){
                // 如果用户名包含 @，则使用邮箱作为查询条件
                query = "SELECT * FROM users WHERE steam64id = ?";
            }else if(username.contains("@"))
            {
                // 邮箱
                query = "SELECT * FROM users WHERE email = ?";
            }
            else{
                // 否则，使用qq作为查询条件
                query = "SELECT * FROM users WHERE qqnumber = ?";
            }
            // 创建 PreparedStatement 对象
            PreparedStatement stmt = conn.prepareStatement(query);
            // 设置查询参数
            stmt.setString(1, username);
            // 执行查询
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // 获取数据库中存储的加密密码
                String hashedPassword = rs.getString("password");

                // 使用 BCrypt 验证密码
                if (BCrypt.checkpw(password, hashedPassword)) {
                    // 登录成功，设置 Session 属性
                    session.setAttribute("userid", rs.getInt("id"));
                    session.setAttribute("userrole", rs.getString("role"));
                    session.setAttribute("usersteam64id", rs.getString("steam64id"));
                    session.setAttribute("userqqnumber", rs.getString("qqnumber"));
                    session.setAttribute("useremail", rs.getString("email"));

                    response.sendRedirect("dashboard.jsp");
                } else {
                    // 重定向到仪表板页面
                    response.sendRedirect("login.jsp?error=invalidCredentials");
                }
            } else {
                // 用户不存在，重定向到登录页面并显示错误信息
                response.sendRedirect("login.jsp?error=invalidCredentials");
            }
        } catch (SQLException e) {
            UserMannageSystemLoger.logger.error(e.getStackTrace());
            response.sendRedirect("login.jsp?error=databaseError");
        } finally {
        }
    }
}
