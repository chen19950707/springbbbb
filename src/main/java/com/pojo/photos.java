package com.pojo;

import java.util.List;

public class photos {
    private Integer id;
    private String tittle;
    private String time;
    private List<photo> photo;

    public List<com.pojo.photo> getPhoto() {
        return photo;
    }

    @Override
    public String toString() {
        return "photos{" +
                "id=" + id +
                ", tittle='" + tittle + '\'' +
                ", time='" + time + '\'' +
                ", photo=" + photo +
                '}';
    }

    public void setPhoto(List<com.pojo.photo> photo) {
        this.photo = photo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }


}
