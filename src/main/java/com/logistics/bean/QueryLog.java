package com.logistics.bean;

import java.util.Date;

public class QueryLog {
    private int id;
    private Integer userId;
    private String trackingNo;
    private Integer companyId;
    private String ip;
    private Date queryTime;
    private String username;
    private String companyName;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getTrackingNo() { return trackingNo; }
    public void setTrackingNo(String trackingNo) { this.trackingNo = trackingNo; }
    public Integer getCompanyId() { return companyId; }
    public void setCompanyId(Integer companyId) { this.companyId = companyId; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public Date getQueryTime() { return queryTime; }
    public void setQueryTime(Date queryTime) { this.queryTime = queryTime; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
}
