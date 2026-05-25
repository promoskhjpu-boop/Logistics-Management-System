<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.NoticeDao" %>
<%@ page import="com.logistics.bean.Notice" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<% List<Notice> list = new NoticeDao().findAll(); SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); %>
<%@ include file="/admin/includes/header.jsp" %>
<div class="admin-header"><h1>公告管理</h1><a href="edit.jsp" class="btn btn-primary">发布公告</a></div>
<% if (request.getParameter("msg") != null) { %><div class="alert alert-success"><%= request.getParameter("msg") %></div><% } %>
<div class="admin-card">
<table class="table"><thead><tr><th>标题</th><th>时间</th><th>操作</th></tr></thead><tbody>
<% for (Notice n : list) { %>
<tr><td><%= n.getTitle() %></td><td><%= sdf.format(n.getCreateTime()) %></td><td>
<a href="edit.jsp?id=<%= n.getId() %>" class="btn btn-primary btn-sm">编辑</a>
<form action="${pageContext.request.contextPath}/admin/notice" method="post" style="display:inline;" onsubmit="return confirm('确定删除？');">
<input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="<%= n.getId() %>">
<button type="submit" class="btn btn-danger btn-sm">删除</button></form></td></tr>
<% } %></tbody></table></div>
<%@ include file="/admin/includes/footer.jsp" %>
