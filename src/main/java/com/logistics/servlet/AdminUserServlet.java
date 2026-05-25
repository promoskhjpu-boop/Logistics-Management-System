package com.logistics.servlet;

import com.logistics.dao.UserDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/user")
public class AdminUserServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        int id = Integer.parseInt(req.getParameter("id"));

        if ("enable".equals(action)) {
            userDao.updateStatus(id, 1);
            resp.sendRedirect(req.getContextPath() + "/admin/user/list.jsp?msg=已启用");
        } else if ("disable".equals(action)) {
            userDao.updateStatus(id, 0);
            resp.sendRedirect(req.getContextPath() + "/admin/user/list.jsp?msg=已禁用");
        }
    }
}
