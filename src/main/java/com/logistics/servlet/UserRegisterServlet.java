package com.logistics.servlet;

import com.logistics.bean.User;
import com.logistics.dao.UserDao;
import com.logistics.util.WebUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/register")
public class UserRegisterServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String nickname = req.getParameter("nickname");
        String phone = req.getParameter("phone");

        if (WebUtil.isEmpty(username) || WebUtil.isEmpty(password)) {
            resp.sendRedirect(req.getContextPath() + "/register.jsp?error=账号和密码不能为空");
            return;
        }

        if (userDao.findByUsername(username) != null) {
            resp.sendRedirect(req.getContextPath() + "/register.jsp?error=账号已存在");
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname != null ? nickname : username);
        user.setPhone(phone != null ? phone : "");

        if (userDao.register(user)) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?msg=注册成功，请登录");
        } else {
            resp.sendRedirect(req.getContextPath() + "/register.jsp?error=注册失败");
        }
    }
}
