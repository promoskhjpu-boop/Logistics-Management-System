<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.CompanyDao" %>
<%@ page import="com.logistics.bean.Company" %>
<%@ page import="java.util.List" %>
<% List<Company> list = new CompanyDao().findAll(); %>
<%@ include file="/admin/includes/header.jsp" %>
<div class="admin-header"><h1>物流公司管理</h1><a href="edit.jsp" class="btn btn-primary">添加公司</a></div>
<% if (request.getParameter("msg") != null) { %><div class="alert alert-success"><%= request.getParameter("msg") %></div><% } %>
<div class="admin-card">
<table class="table"><thead><tr><th>名称</th><th>编码</th><th>电话</th><th>状态</th><th>操作</th></tr></thead><tbody>
<% for (Company c : list) { %>
<tr><td><%= c.getName() %></td><td><%= c.getCode() %></td><td><%= c.getPhone() %></td><td><%= c.getEnabled()==1?"启用":"禁用" %></td>
<td><a href="edit.jsp?id=<%= c.getId() %>" class="btn btn-primary btn-sm">编辑</a>
<form action="${pageContext.request.contextPath}/admin/company" method="post" style="display:inline;" onsubmit="return confirm('确定删除？');">
<input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="<%= c.getId() %>">
<button type="submit" class="btn btn-danger btn-sm">删除</button></form></td></tr>
<% } %></tbody></table></div>
<%@ include file="/admin/includes/footer.jsp" %>
