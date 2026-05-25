package com.logistics.servlet;

import com.logistics.bean.LoginLog;
import com.logistics.bean.User;
import com.logistics.dao.LoginLogDao;
import com.logistics.dao.UserDao;
import com.logistics.util.WebUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user/login")
public class UserLoginServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();
    private final LoginLogDao loginLogDao = new LoginLogDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 添加详细的调试日志
        System.out.println("=== 登录请求开始 ===");
        System.out.println("请求URI: " + req.getRequestURI());
        System.out.println("请求方式: " + req.getMethod());
        
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        System.out.println("用户名: " + username);
        System.out.println("密码长度: " + (password != null ? password.length() : "null"));
        
        try {
            // 1. 验证用户
            User user = userDao.login(username, password);
            System.out.println("登录查询结果: " + (user != null ? "成功找到用户" : "用户不存在或密码错误"));
            
            // 2. 创建登录日志
            LoginLog log = new LoginLog();
            log.setUserType(0);
            log.setUsername(username != null ? username : "unknown");
            log.setIp(WebUtil.getClientIp(req));
            
            if (user != null) {
                // 登录成功
                log.setUserId(user.getId());
                log.setSuccess(1);
                
                try {
                    loginLogDao.add(log);
                    System.out.println("登录日志记录成功");
                } catch (Exception e) {
                    System.err.println("记录登录日志失败: " + e.getMessage());
                    e.printStackTrace();
                    // 继续执行，不因为日志失败而影响登录
                }
                
                // 设置会话
                req.getSession().setAttribute("user", user);
                System.out.println("Session设置完成，用户ID: " + user.getId());
                
                // 重定向到首页
                String redirectUrl = req.getContextPath() + "/index.jsp";
                System.out.println("准备重定向到: " + redirectUrl);
                
                // 确保响应头正确
                resp.setStatus(302);
                resp.setHeader("Location", redirectUrl);
                resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                
                // 执行重定向
                resp.sendRedirect(redirectUrl);
                System.out.println("重定向已发送");
                
            } else {
                // 登录失败
                log.setSuccess(0);
                
                try {
                    loginLogDao.add(log);
                    System.out.println("失败日志记录成功");
                } catch (Exception e) {
                    System.err.println("记录失败日志失败: " + e.getMessage());
                }
                
                // 重定向回登录页
                String redirectUrl = req.getContextPath() + "/login.jsp?error=用户名或密码错误";
                System.out.println("登录失败，重定向到: " + redirectUrl);
                
                resp.setStatus(302);
                resp.setHeader("Location", redirectUrl);
                resp.sendRedirect(redirectUrl);
            }
            
        } catch (Exception e) {
            // 全局异常处理
            System.err.println("登录过程中发生异常: ");
            e.printStackTrace();
            
            // 即使异常也要返回有效的响应
            resp.setStatus(500);
            resp.setContentType("text/html;charset=UTF-8");
            
            PrintWriter out = resp.getWriter();
            out.println("<html><head><title>登录错误</title></head>");
            out.println("<body><h2>系统错误</h2>");
            out.println("<p>登录过程中出现错误: " + e.getMessage() + "</p>");
            out.println("<p><a href='" + req.getContextPath() + "/login.jsp'>返回登录页</a></p>");
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
            out.println("</body></html>");
            
            // 也尝试重定向
            try {
                resp.sendRedirect(req.getContextPath() + "/login.jsp?error=系统错误，请联系管理员");
            } catch (Exception ex) {
                // 忽略二次异常
            }
        }
        
        System.out.println("=== 登录请求处理结束 ===");
    }
}