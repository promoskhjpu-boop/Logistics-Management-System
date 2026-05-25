package com.logistics.servlet;

import com.logistics.bean.Express;
import com.logistics.dao.ExpressDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/express")
public class AdminExpressServlet extends HttpServlet {
    private final ExpressDao expressDao = new ExpressDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("add".equals(action) || "edit".equals(action)) {
            Express express = new Express();
            if ("edit".equals(action)) {
                express.setId(Integer.parseInt(req.getParameter("id")));
            }
            express.setTrackingNo(req.getParameter("trackingNo"));
            String userIdStr = req.getParameter("userId");
            if (userIdStr != null && !userIdStr.isEmpty()) {
                express.setUserId(Integer.parseInt(userIdStr));
            }
            express.setCompanyId(Integer.parseInt(req.getParameter("companyId")));
            express.setReceiver(req.getParameter("receiver"));
            express.setSender(req.getParameter("sender"));
            express.setStatus(Integer.parseInt(req.getParameter("status")));

            if ("add".equals(action)) {
                expressDao.add(express);
            } else {
                expressDao.update(express);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/express/list.jsp?msg=保存成功");
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            expressDao.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/express/list.jsp?msg=删除成功");
        }
    }
}
