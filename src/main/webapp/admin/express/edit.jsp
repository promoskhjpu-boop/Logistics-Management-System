<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.*" %>
<%@ page import="com.logistics.bean.*" %>
<%@ page import="java.util.List" %>
<%
    Express express = null;
    String action = "add";
    if (request.getParameter("id") != null) {
        express = new ExpressDao().findById(Integer.parseInt(request.getParameter("id")));
        action = "edit";
    }
    List<Company> companies = new CompanyDao().findAll();
    List<User> users = new UserDao().findAll();
%>
<%@ include file="/admin/includes/header.jsp" %>
<div class="admin-header"><h1><%= "edit".equals(action) ? "编辑快递" : "添加快递" %></h1></div>
<div class="admin-card" style="max-width:600px;">
    <form action="${pageContext.request.contextPath}/admin/express" method="post">
        <input type="hidden" name="action" value="<%= action %>">
        <% if (express != null) { %><input type="hidden" name="id" value="<%= express.getId() %>"><% } %>
        <div class="form-group"><label>快递单号</label><input type="text" name="trackingNo" class="form-control" required value="<%= express != null ? express.getTrackingNo() : "" %>"></div>
        <div class="form-group"><label>快递公司</label><select name="companyId" class="form-control" required>
            <% for (Company c : companies) { %><option value="<%= c.getId() %>" <%= express!=null&&express.getCompanyId()==c.getId()?"selected":"" %>><%= c.getName() %></option><% } %>
        </select></div>
        <div class="form-group"><label>绑定用户</label><select name="userId" class="form-control">
            <option value="">不绑定</option>
            <% for (User u : users) { %><option value="<%= u.getId() %>" <%= express!=null&&express.getUserId()!=null&&express.getUserId().equals(u.getId())?"selected":"" %>><%= u.getUsername() %></option><% } %>
        </select></div>
        <div class="form-group"><label>发件人</label><input type="text" name="sender" class="form-control" value="<%= express != null && express.getSender()!=null ? express.getSender() : "" %>"></div>
        <div class="form-group"><label>收件人</label><input type="text" name="receiver" class="form-control" value="<%= express != null && express.getReceiver()!=null ? express.getReceiver() : "" %>"></div>
        <div class="form-group"><label>状态</label><select name="status" class="form-control">
            <option value="0" <%= express!=null&&express.getStatus()==0?"selected":"" %>>待揽收</option>
            <option value="1" <%= express!=null&&express.getStatus()==1?"selected":"" %>>运输中</option>
            <option value="2" <%= express!=null&&express.getStatus()==2?"selected":"" %>>派送中</option>
            <option value="3" <%= express!=null&&express.getStatus()==3?"selected":"" %>>已签收</option>
        </select></div>
        <button type="submit" class="btn btn-primary">保存</button>
        <a href="list.jsp" class="btn btn-secondary">返回</a>
    </form>
</div>
<%@ include file="/admin/includes/footer.jsp" %>
