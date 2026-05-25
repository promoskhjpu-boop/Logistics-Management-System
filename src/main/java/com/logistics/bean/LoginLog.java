package com.logistics.bean;

import java.util.Date;

public class LoginLog {
    private int id;
    private int userType;
    private Integer userId;
    private String username;
    private String ip;
    private int success;
    private Date loginTime;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserType() { return userType; }
    public void setUserType(int userType) { this.userType = userType; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public int getSuccess() { return success; }
    public void setSuccess(int success) { this.success = success; }
    public Date getLoginTime() { return loginTime; }
    public void setLoginTime(Date loginTime) { this.loginTime = loginTime; }

    public String getUserTypeText() {
        return userType == 1 ? "管理员" : "用户";
    }
}
