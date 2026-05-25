package com.logistics.servlet;

import com.logistics.bean.Express;
import com.logistics.bean.User;
import com.logistics.dao.ExpressDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/express")
public class UserExpressServlet extends HttpServlet {
    private final ExpressDao expressDao = new ExpressDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            String trackingNo = req.getParameter("trackingNo");
            int companyId = Integer.parseInt(req.getParameter("companyId"));
            Express existing = expressDao.findByTrackingNo(trackingNo);
            if (existing != null) {
                if (existing.getUserId() != null && !existing.getUserId().equals(user.getId())) {
                    resp.sendRedirect(req.getContextPath() + "/user/my-express.jsp?error=该快递已被其他用户绑定");
                    return;
                }
                expressDao.bindToUser(existing.getId(), user.getId());
            } else {
                Express express = new Express();
                express.setTrackingNo(trackingNo);
                express.setUserId(user.getId());
                express.setCompanyId(companyId);
                express.setStatus(0);
                expressDao.add(express);
            }
            resp.sendRedirect(req.getContextPath() + "/user/my-express.jsp?msg=添加成功");
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Express express = expressDao.findById(id);
            if (express != null && express.getUserId() != null && express.getUserId() == user.getId()) {
                Express update = new Express();
                update.setId(id);
                update.setTrackingNo(express.getTrackingNo());
                update.setUserId(null);
                update.setCompanyId(express.getCompanyId());
                update.setReceiver(express.getReceiver());
                update.setSender(express.getSender());
                update.setStatus(express.getStatus());
                expressDao.update(update);
            }
            resp.sendRedirect(req.getContextPath() + "/user/my-express.jsp?msg=已移除");
        } else if ("sign".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Express express = expressDao.findById(id);
            if (express != null && express.getUserId() != null && express.getUserId() == user.getId()) {
                expressDao.updateStatus(id, 3);
            }
            resp.sendRedirect(req.getContextPath() + "/user/my-express.jsp?msg=已标记签收");
        }
    }
}
