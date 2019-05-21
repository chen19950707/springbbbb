package com.pojo;

public class user {
    public String name;
    public String pwd;
    public Integer age;
    public String img;
    public String sex;
    public String mail;
    public Integer id;

    @Override
    public String toString() {
        return "user{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", age=" + age +
                ", img='" + img + '\'' +
                ", sex='" + sex + '\'' +
                ", mail='" + mail + '\'' +
                ", id=" + id +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public user() {
    }

    public user(String name, String pwd, Integer age, String img, String sex, String mail) {
        this.name = name;
        this.pwd = pwd;
        this.age = age;
        this.img = img;
        this.sex = sex;
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
