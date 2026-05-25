package com.logistics.servlet;

import com.logistics.dao.UserDao;
import com.logistics.util.WebUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/reset-password")
public class ResetPasswordServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String phone = req.getParameter("phone");
        String newPassword = req.getParameter("newPassword");

        if (WebUtil.isEmpty(username) || WebUtil.isEmpty(phone) || WebUtil.isEmpty(newPassword)) {
            resp.sendRedirect(req.getContextPath() + "/forgot-password.jsp?error=请填写完整信息");
            return;
        }

        if (userDao.resetPassword(username, phone, newPassword)) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?msg=密码重置成功，请登录");
        } else {
            resp.sendRedirect(req.getContextPath() + "/forgot-password.jsp?error=账号或手机号不匹配");
        }
    }
}
