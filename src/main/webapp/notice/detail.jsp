<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.NoticeDao" %>
<%@ page import="com.logistics.bean.Notice" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    Notice notice = new NoticeDao().findById(id);
    if (notice == null) { response.sendRedirect(request.getContextPath() + "/notice/list.jsp"); return; }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%>
<%@ include file="../includes/header.jsp" %>

<div class="container section">
    <h2 class="page-title"><%= notice.getTitle() %></h2>
    <div class="card">
        <p style="color:#6b7280;margin-bottom:16px;">发布时间：<%= sdf.format(notice.getCreateTime()) %></p>
        <div style="line-height:1.8;white-space:pre-wrap;"><%= notice.getContent() %></div>
        <p style="margin-top:20px;"><a href="${pageContext.request.contextPath}/notice/list.jsp" class="btn btn-secondary">返回列表</a></p>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>
