package com.logistics.bean;

import java.util.Date;

public class Track {
    private int id;
    private int expressId;
    private Date trackTime;
    private String content;
    private int status;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getExpressId() { return expressId; }
    public void setExpressId(int expressId) { this.expressId = expressId; }
    public Date getTrackTime() { return trackTime; }
    public void setTrackTime(Date trackTime) { this.trackTime = trackTime; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getStatusText() {
        switch (status) {
            case 0: return "揽收";
            case 1: return "运输";
            case 2: return "中转";
            case 3: return "派送";
            case 4: return "签收";
            default: return "其他";
        }
    }
}
