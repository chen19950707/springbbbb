package com.pojo;

import java.util.List;

public class reply {
    private Integer rid;
    private String content;
    private String master;
    private String masterImg;
    private String addr;
    private String time;
    private Integer reid;

    @Override
    public String toString() {
        return "reply{" +
                "rid=" + rid +
                ", content='" + content + '\'' +
                ", master='" + master + '\'' +
                ", masterImg='" + masterImg + '\'' +
                ", addr='" + addr + '\'' +
                ", time='" + time + '\'' +
                ", reid=" + reid +
                ", inreply=" + inreply +
                '}';
    }

    public Integer getReid() {
        return reid;
    }

    public void setReid(Integer reid) {
        this.reid = reid;
    }

    private List<reply> inreply;

    public List<reply> getInreply() {
        return inreply;
    }

    public void setInreply(List<reply> inreply) {
        this.inreply = inreply;
    }

    public String getMasterImg() {
        return masterImg;
    }

    public void setMasterImg(String masterImg) {
        this.masterImg = masterImg;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
