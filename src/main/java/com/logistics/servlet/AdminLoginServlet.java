package com.logistics.servlet;

import com.logistics.bean.Admin;
import com.logistics.bean.LoginLog;
import com.logistics.dao.AdminDao;
import com.logistics.dao.LoginLogDao;
import com.logistics.util.WebUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/login")
public class AdminLoginServlet extends HttpServlet {
    private final AdminDao adminDao = new AdminDao();
    private final LoginLogDao loginLogDao = new LoginLogDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Admin admin = adminDao.login(username, password);
        LoginLog log = new LoginLog();
        log.setUserType(1);
        log.setUsername(username);
        log.setIp(WebUtil.getClientIp(req));

        if (admin != null) {
            log.setUserId(admin.getId());
            log.setSuccess(1);
            loginLogDao.add(log);
            req.getSession().setAttribute("admin", admin);
            resp.sendRedirect(req.getContextPath() + "/admin/index.jsp");
        } else {
            log.setSuccess(0);
            loginLogDao.add(log);
            resp.sendRedirect(req.getContextPath() + "/admin/login.jsp?error=账号或密码错误");
        }
    }
}
