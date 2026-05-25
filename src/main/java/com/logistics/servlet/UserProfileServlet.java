package com.logistics.servlet;

import com.logistics.bean.User;
import com.logistics.dao.UserDao;
import com.logistics.util.MD5Util;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/profile")
public class UserProfileServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User sessionUser = (User) req.getSession().getAttribute("user");
        if (sessionUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String action = req.getParameter("action");
        if ("password".equals(action)) {
            String oldPassword = req.getParameter("oldPassword");
            String newPassword = req.getParameter("newPassword");
            User dbUser = userDao.findById(sessionUser.getId());
            if (dbUser == null || !dbUser.getPassword().equals(MD5Util.encrypt(oldPassword))) {
                resp.sendRedirect(req.getContextPath() + "/user/profile.jsp?error=原密码错误");
                return;
            }
            userDao.updatePassword(sessionUser.getId(), newPassword);
            resp.sendRedirect(req.getContextPath() + "/user/profile.jsp?msg=密码修改成功");
        } else {
            User user = new User();
            user.setId(sessionUser.getId());
            user.setNickname(req.getParameter("nickname"));
            user.setPhone(req.getParameter("phone"));
            userDao.updateProfile(user);
            sessionUser.setNickname(user.getNickname());
            sessionUser.setPhone(user.getPhone());
            req.getSession().setAttribute("user", sessionUser);
            resp.sendRedirect(req.getContextPath() + "/user/profile.jsp?msg=信息更新成功");
        }
    }
}
