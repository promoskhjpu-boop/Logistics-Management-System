package com.logistics.servlet;

import com.logistics.bean.Track;
import com.logistics.dao.ExpressDao;
import com.logistics.dao.TrackDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/admin/track")
public class AdminTrackServlet extends HttpServlet {
    private final TrackDao trackDao = new TrackDao();
    private final ExpressDao expressDao = new ExpressDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        int expressId = Integer.parseInt(req.getParameter("expressId"));

        if ("add".equals(action) || "edit".equals(action)) {
            Track track = new Track();
            if ("edit".equals(action)) {
                track.setId(Integer.parseInt(req.getParameter("id")));
            }
            track.setExpressId(expressId);
            track.setContent(req.getParameter("content"));
            track.setStatus(Integer.parseInt(req.getParameter("status")));

            String trackTimeStr = req.getParameter("trackTime");
            if (trackTimeStr == null || trackTimeStr.isEmpty()) {
                track.setTrackTime(new Date());
            } else {
                try {
                    track.setTrackTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(trackTimeStr));
                } catch (Exception e) {
                    track.setTrackTime(new Date());
                }
            }

            if ("add".equals(action)) {
                trackDao.add(track);
            } else {
                trackDao.update(track);
            }

            updateExpressStatus(expressId, track.getStatus());
            resp.sendRedirect(req.getContextPath() + "/admin/track/edit.jsp?expressId=" + expressId + "&msg=保存成功");
        } else if ("delete".equals(action)) {
            trackDao.delete(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/admin/track/edit.jsp?expressId=" + expressId + "&msg=删除成功");
        }
    }

    private void updateExpressStatus(int expressId, int trackStatus) {
        int expressStatus;
        switch (trackStatus) {
            case 0: expressStatus = 0; break;
            case 1:
            case 2: expressStatus = 1; break;
            case 3: expressStatus = 2; break;
            case 4: expressStatus = 3; break;
            default: return;
        }
        expressDao.updateStatus(expressId, expressStatus);
    }
}
