<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>

<%
    try {
        // 1. 加载驱动（MySQL 8+ 自动加载）
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        // 2. 建立连接（改成你自己的数据库信息）
        String url = "jdbc:mysql://localhost:3306/logistics?useSSL=false&serverTimezone=Asia/Shanghai";
        String user = "root";
        String password = "123456";
        
        Connection conn = DriverManager.getConnection(url, user, password);
        
        out.println("✅ 数据库连接成功！");
        out.println("<br>连接对象：" + conn);
        
        conn.close();
    } catch (Exception e) {
        out.println("❌ 连接失败！");
        out.println("<br>错误信息：" + e.getMessage());
        e.printStackTrace(new java.io.PrintWriter(out));
    }
%>