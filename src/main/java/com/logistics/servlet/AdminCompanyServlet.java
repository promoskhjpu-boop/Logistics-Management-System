package com.logistics.servlet;

import com.logistics.bean.Company;
import com.logistics.dao.CompanyDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/admin/company")
@MultipartConfig
public class AdminCompanyServlet extends HttpServlet {
    private final CompanyDao companyDao = new CompanyDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();
        if (contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
            handleMultipart(req, resp);
            return;
        }

        String action = req.getParameter("action");
        if ("delete".equals(action)) {
            companyDao.delete(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/admin/company/list.jsp?msg=删除成功");
        }
    }

    private void handleMultipart(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            Company company = new Company();
            String action = req.getParameter("action");
            String existingLogo = req.getParameter("existingLogo");

            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                company.setId(Integer.parseInt(idStr));
            }
            company.setName(req.getParameter("name"));
            company.setCode(req.getParameter("code"));
            company.setPhone(req.getParameter("phone"));
            company.setWebsite(req.getParameter("website"));
            company.setEnabled(Integer.parseInt(req.getParameter("enabled")));

            Part logoPart = req.getPart("logo");
            if (logoPart != null && logoPart.getSize() > 0) {
                String fileName = logoPart.getSubmittedFileName();
                String ext = fileName != null && fileName.contains(".")
                        ? fileName.substring(fileName.lastIndexOf('.')) : ".png";
                String newName = UUID.randomUUID().toString().replace("-", "") + ext;
                String uploadPath = req.getServletContext().getRealPath("/images/companies");
                File dir = new File(uploadPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir, newName);
                logoPart.write(file.getAbsolutePath());
                company.setLogo(req.getContextPath() + "/images/companies/" + newName);
            } else if (existingLogo != null && !existingLogo.isEmpty()) {
                company.setLogo(existingLogo);
            }

            if ("edit".equals(action)) {
                companyDao.update(company);
            } else {
                companyDao.add(company);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/company/list.jsp?msg=保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/admin/company/list.jsp?error=保存失败");
        }
    }
}
