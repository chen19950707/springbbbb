package com.dao;

import com.pojo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Component
public interface UserDao {
    user getUser(user user);
    String checkName(user user);
    Integer saveUser(user user);
    Integer saveArticle(
            @Param("tittle") String tittle,
            @Param("content") String content,
            @Param("time") String time,
            @Param("type") String type
    );
    List<article> getArticle(@Param("type") String type,@Param("num") Integer num);//获取所有文章
    List<article> getHot();//获取回复量最多的5条文章
    List<article> getPoint();//获取点击量最多的10条文章
    article getOneArticle(Integer id);//获取某条文章的全部信息
    article getPre(Integer id);//获取前一篇文章
    article getNext(Integer id);//获取下一篇文章
    Integer setPoint(
            @Param("id") Integer id,
            @Param("pcount") Integer pcount
    );//增加点击量
    Integer setArticleReply(
            @Param("rcount")Integer rcount,
            @Param("id")Integer id
    );//增加文章回复量
    Integer setReply(reply reply);//发表回复
    List<reply> getReply(@Param("addr") String addr,@Param("num") Integer num);//获取留言板回复
    List<reply> getArticleReply(@Param("reid") Integer reid,@Param("num") Integer num);
    Integer savePtittle(photos photos);//存储照片主题
    Integer savePhoto(photo photo);//存储照片
    List<photos> getPhotos(Integer num);//获取照片
    photos getPtittles(photos photos);
    photos getPhotoo(Integer id);//获取某一相册
    List<photo> getPhoto(Integer id);//获取相册内的照片
}
