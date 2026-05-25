<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.CompanyDao" %>
<%@ page import="com.logistics.bean.Company" %>
<% Company company = null; String action = "add";
if (request.getParameter("id") != null) { company = new CompanyDao().findById(Integer.parseInt(request.getParameter("id"))); action = "edit"; } %>
<%@ include file="/admin/includes/header.jsp" %>
<div class="admin-header"><h1><%= "edit".equals(action) ? "编辑公司" : "添加公司" %></h1></div>
<div class="admin-card" style="max-width:600px;">
<form action="${pageContext.request.contextPath}/admin/company" method="post" enctype="multipart/form-data">
<input type="hidden" name="action" value="<%= action %>">
<% if (company != null) { %><input type="hidden" name="id" value="<%= company.getId() %>"><% } %>
<input type="hidden" name="existingLogo" value="<%= company != null && company.getLogo()!=null ? company.getLogo() : "" %>">
<div class="form-group"><label>公司名称</label><input type="text" name="name" class="form-control" required value="<%= company != null ? company.getName() : "" %>"></div>
<div class="form-group"><label>编码</label><input type="text" name="code" class="form-control" required value="<%= company != null ? company.getCode() : "" %>"></div>
<div class="form-group"><label>客服电话</label><input type="text" name="phone" class="form-control" value="<%= company != null ? company.getPhone() : "" %>"></div>
<div class="form-group"><label>官网</label><input type="text" name="website" class="form-control" value="<%= company != null ? company.getWebsite() : "" %>"></div>
<div class="form-group"><label>Logo</label><input type="file" name="logo" class="form-control"></div>
<div class="form-group"><label>状态</label><select name="enabled" class="form-control">
<option value="1" <%= company==null||company.getEnabled()==1?"selected":"" %>>启用</option>
<option value="0" <%= company!=null&&company.getEnabled()==0?"selected":"" %>>禁用</option></select></div>
<button type="submit" class="btn btn-primary">保存</button> <a href="list.jsp" class="btn btn-secondary">返回</a>
</form></div>
<%@ include file="/admin/includes/footer.jsp" %>
