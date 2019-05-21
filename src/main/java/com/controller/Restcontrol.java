package com.controller;

import com.pojo.user;
import com.service.userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/util")
public class Restcontrol {
    @Autowired
    userservice userservice;
    //获取相册
    @RequestMapping("/getPhoto")
    public Map getPhoto(HttpServletRequest request){
        Integer id =Integer.valueOf(request.getParameter("id"));
        Map photo = userservice.getPhoto(id);
        return photo;
    }
    //上传相册
    @RequestMapping("/upload")
    public Map upload(HttpServletRequest request,@RequestParam(value = "files",required = false) MultipartFile[] files) throws Exception {
        HashMap<String,String> as = new HashMap<>();
        user user =(user) request.getSession().getAttribute("user");
        for (MultipartFile file : files) {
            System.out.println(file.getOriginalFilename());
        }
        if(user==null || !user.getName().equals("admin")){
            as.put("res","未登录或无权限");
            return as;
        }
        userservice.upPhoto(request,files);
        as.put("res","上传成功");
        return as;
    }
    //验证登录
    @RequestMapping("/check")
    public String check(HttpServletRequest request){
        user user =(user) request.getSession().getAttribute("user");
        if(user==null){
            return "false";
        }else{
            return "true";
        }
    }
    //发表文章接收数据
    @RequestMapping("/publish")
    public String publish(HttpServletRequest request, HttpServletResponse response) {
        Integer article = userservice.saveArticle(request, response);
        if(article==1){
            return "发表成功!";
        }
        return "发表失败!";
    }

    //验证用户，将用户信息存入数据库
    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String mailcode = userservice.mailcode(request, response);
        if (mailcode.equals("true")) {
            if(userservice.saveUser(request, response)==1){
                return "true";
            }else{
                return "err";
            }
        } else {
            return "false";
        }
    }

    //验证用户名是否已注册
    @RequestMapping("/checkname")
    public String checkname(HttpServletRequest request, HttpServletResponse response) {
        if (userservice.checkname(request, response) == null) {
            return "true";
        } else {
            return "false";
        }
    }

    //验证邮箱验证码
    @RequestMapping("/checkmail")
    public String checkmail(HttpServletRequest request, HttpServletResponse response) {
        return userservice.mailcode(request, response);
    }

    //发送邮件倒计时
    @RequestMapping("/timee")
    public String daoTime(HttpServletRequest request) throws Exception {
        Integer timee = (Integer) request.getSession().getAttribute("time");
        if (timee != null) {
            timee--;
            System.out.println(timee);
            request.getSession().setAttribute("time", timee);
        }
        return String.valueOf(timee);
    }

    //发送邮箱验证码
    @RequestMapping("/sendCode")
    public String sendCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return userservice.sendCode(request, response);
    }

    //登录验证用户信息
    @RequestMapping("/checkUser")
    public String checkuser(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        return userservice.checkuser(request, response,model);
    }

    //验证图片验证码
    @RequestMapping("/checkCode")
    public String check(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return userservice.checkcode(request, response);
    }

    //图片验证码
    @RequestMapping("/img")
    public void img(HttpServletRequest request, HttpServletResponse response) throws Exception {
        userservice.imgcode(request, response);
    }
}
