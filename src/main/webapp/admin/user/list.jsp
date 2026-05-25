<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.*" %>
<%@ page import="com.logistics.bean.*" %>
<%@ page import="java.util.List" %>
<% List<User> users = new UserDao().findAll(); %>
<%@ include file="/admin/includes/header.jsp" %>
<div class="admin-header"><h1>用户管理</h1></div>
<% if (request.getParameter("msg") != null) { %><div class="alert alert-success"><%= request.getParameter("msg") %></div><% } %>
<div class="admin-card">
<table class="table"><thead><tr><th>ID</th><th>账号</th><th>昵称</th><th>手机</th><th>状态</th><th>绑定快递</th><th>操作</th></tr></thead><tbody>
<% for (User u : users) { List<Express> exList = new ExpressDao().findByUserId(u.getId()); %>
<tr><td><%= u.getId() %></td><td><%= u.getUsername() %></td><td><%= u.getNickname() %></td><td><%= u.getPhone() %></td>
<td><%= u.getStatus()==1?"正常":"禁用" %></td><td><%= exList.size() %> 个</td><td>
<% if (u.getStatus()==1) { %><form action="${pageContext.request.contextPath}/admin/user" method="post" style="display:inline;">
<input type="hidden" name="action" value="disable"><input type="hidden" name="id" value="<%= u.getId() %>">
<button type="submit" class="btn btn-danger btn-sm">禁用</button></form><% } else { %>
<form action="${pageContext.request.contextPath}/admin/user" method="post" style="display:inline;">
<input type="hidden" name="action" value="enable"><input type="hidden" name="id" value="<%= u.getId() %>">
<button type="submit" class="btn btn-success btn-sm">启用</button></form><% } %></td></tr>
<% } %></tbody></table></div>
<%@ include file="/admin/includes/footer.jsp" %>
