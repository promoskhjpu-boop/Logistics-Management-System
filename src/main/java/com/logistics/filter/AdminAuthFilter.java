package com.logistics.filter;

import com.logistics.bean.Admin;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/admin/*")
public class AdminAuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (uri.endsWith("/admin/login.jsp") || uri.contains("/admin/login") || uri.contains("/admin/logout")) {
            chain.doFilter(request, response);
            return;
        }
        HttpSession session = req.getSession(false);
        Admin admin = session != null ? (Admin) session.getAttribute("admin") : null;
        if (admin == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login.jsp");
            return;
        }
        chain.doFilter(request, response);
    }
}
