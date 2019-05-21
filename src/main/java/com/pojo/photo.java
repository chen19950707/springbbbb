package com.pojo;

public class photo {
    private String url;
    private Integer tid;

    @Override
    public String toString() {
        return "photo{" +
                "url='" + url + '\'' +
                ", tid=" + tid +
                '}';
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
