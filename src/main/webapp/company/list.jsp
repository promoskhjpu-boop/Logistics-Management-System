<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.CompanyDao" %>
<%@ page import="com.logistics.bean.Company" %>
<%@ page import="java.util.List" %>
<% List<Company> companies = new CompanyDao().findEnabled(); %>
<%@ include file="../includes/header.jsp" %>

<div class="container section">
    <h2 class="page-title">快递公司</h2>
    <div class="grid grid-3">
        <% for (Company c : companies) { %>
        <div class="card company-card">
            <div class="company-logo"><%= c.getCode() %></div>
            <div class="company-name"><%= c.getName() %></div>
            <p class="company-phone">客服电话：<%= c.getPhone() %></p>
            <% if (c.getWebsite() != null && !c.getWebsite().isEmpty()) { %>
            <p><a href="<%= c.getWebsite() %>" target="_blank">访问官网</a></p>
            <% } %>
        </div>
        <% } %>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>
