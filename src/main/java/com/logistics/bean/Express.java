package com.logistics.bean;

import java.util.Date;
import java.util.List;

public class Express {
    private int id;
    private String trackingNo;
    private Integer userId;
    private int companyId;
    private String receiver;
    private String sender;
    private int status;
    private Date createTime;

    private String companyName;
    private String companyCode;
    private String username;
    private List<Track> tracks;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTrackingNo() { return trackingNo; }
    public void setTrackingNo(String trackingNo) { this.trackingNo = trackingNo; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public int getCompanyId() { return companyId; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }
    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getCompanyCode() { return companyCode; }
    public void setCompanyCode(String companyCode) { this.companyCode = companyCode; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public List<Track> getTracks() { return tracks; }
    public void setTracks(List<Track> tracks) { this.tracks = tracks; }

    public String getStatusText() {
        switch (status) {
            case 0: return "待揽收";
            case 1: return "运输中";
            case 2: return "派送中";
            case 3: return "已签收";
            default: return "未知";
        }
    }
}
