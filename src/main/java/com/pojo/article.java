package com.pojo;

public class article {
    private Integer id;//文章ID
    private String tittle;//文章标题
    private String time;//发布时间
    private String author;//作者
    private String content;//文章内容
    private String word;//文章文字内容
    private String img;//文章第一张图片
    private String pcount;//点击数
    private Integer rcount;//评论数
    private String type;//文章类型

    @Override
    public String toString() {
        return "article{" +
                "id=" + id +
                ", tittle='" + tittle + '\'' +
                ", time='" + time + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", word='" + word + '\'' +
                ", img='" + img + '\'' +
                ", pcount='" + pcount + '\'' +
                ", rcount='" + rcount + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPcount() {
        return pcount;
    }

    public void setPcount(String pcount) {
        this.pcount = pcount;
    }

    public Integer getRcount() {
        return rcount;
    }

    public void setRcount(Integer rcount) {
        this.rcount = rcount;
    }
}
