package com.logistics.servlet;

import com.logistics.bean.Notice;
import com.logistics.dao.NoticeDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/notice")
public class AdminNoticeServlet extends HttpServlet {
    private final NoticeDao noticeDao = new NoticeDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("add".equals(action) || "edit".equals(action)) {
            Notice notice = new Notice();
            if ("edit".equals(action)) {
                notice.setId(Integer.parseInt(req.getParameter("id")));
            }
            notice.setTitle(req.getParameter("title"));
            notice.setContent(req.getParameter("content"));

            if ("add".equals(action)) {
                noticeDao.add(notice);
            } else {
                noticeDao.update(notice);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/notice/list.jsp?msg=保存成功");
        } else if ("delete".equals(action)) {
            noticeDao.delete(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/admin/notice/list.jsp?msg=删除成功");
        }
    }
}
