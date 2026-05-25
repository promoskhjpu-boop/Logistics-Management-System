package com.logistics.servlet;

import com.logistics.bean.Admin;
import com.logistics.dao.AdminDao;
import com.logistics.util.MD5Util;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/profile")
public class AdminProfileServlet extends HttpServlet {
    private final AdminDao adminDao = new AdminDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Admin admin = (Admin) req.getSession().getAttribute("admin");
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");

        Admin dbAdmin = adminDao.findById(admin.getId());
        if (dbAdmin == null || !dbAdmin.getPassword().equals(MD5Util.encrypt(oldPassword))) {
            resp.sendRedirect(req.getContextPath() + "/admin/profile.jsp?error=原密码错误");
            return;
        }
        adminDao.updatePassword(admin.getId(), newPassword);
        resp.sendRedirect(req.getContextPath() + "/admin/profile.jsp?msg=密码修改成功");
    }
}
