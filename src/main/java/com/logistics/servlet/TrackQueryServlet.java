package com.logistics.servlet;

import com.logistics.bean.*;
import com.logistics.dao.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/track/query")
public class TrackQueryServlet extends HttpServlet {
    private final ExpressDao expressDao = new ExpressDao();
    private final TrackDao trackDao = new TrackDao();
    private final CompanyDao companyDao = new CompanyDao();
    private final QueryLogDao queryLogDao = new QueryLogDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String trackingNo = req.getParameter("trackingNo");
        String companyIdStr = req.getParameter("companyId");

        if (trackingNo == null || trackingNo.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp?error=请输入快递单号");
            return;
        }

        trackingNo = trackingNo.trim();
        Express express = expressDao.findByTrackingNo(trackingNo);

        if (express == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp?error=未找到该快递信息&trackingNo=" + trackingNo);
            return;
        }

        if (companyIdStr != null && !companyIdStr.isEmpty()) {
            int companyId = Integer.parseInt(companyIdStr);
            if (express.getCompanyId() != companyId) {
                resp.sendRedirect(req.getContextPath() + "/index.jsp?error=快递公司与单号不匹配&trackingNo=" + trackingNo);
                return;
            }
        }

        List<Track> tracks = trackDao.findByExpressId(express.getId());
        express.setTracks(tracks);

        QueryLog log = new QueryLog();
        User user = (User) req.getSession().getAttribute("user");
        if (user != null) log.setUserId(user.getId());
        log.setTrackingNo(trackingNo);
        log.setCompanyId(express.getCompanyId());
        log.setIp(com.logistics.util.WebUtil.getClientIp(req));
        queryLogDao.add(log);

        req.setAttribute("express", express);
        req.setAttribute("tracks", tracks);
        req.getRequestDispatcher("/express-detail.jsp").forward(req, resp);
    }
}
