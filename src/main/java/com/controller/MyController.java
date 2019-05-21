package com.controller;

import com.pojo.article;
import com.pojo.user;
import com.service.userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MyController {
    @Autowired
    userservice userservice;

    @RequestMapping("/hello")
    public String heello() {
        return "hello";
    }

    @RequestMapping("/index")
    public String c1(HttpServletRequest request, Model model) {
        request.getSession().setAttribute("user", request.getSession().getAttribute("user"));
        userservice.getArt(request, null, 10, model);
        return "index";
    }

    @RequestMapping("index/more")
    public String more(HttpServletRequest request, Model model) {
        List<article> more = userservice.getMore(request);
        model.addAttribute("article", more);
        return "index::div1";
    }

    @RequestMapping("/album")
    public String album(HttpServletRequest request, Model model) {
        userservice.getPhotos(request,model,8);
        return "album";
    }

    @RequestMapping("/login")
    public String c6(HttpServletRequest request) {
        return "login";
    }

    @RequestMapping("/register")
    public String c7(HttpServletRequest request) {

        return "register";
    }

    @RequestMapping("/about")
    public String c2(HttpServletRequest request) {

        return "about";
    }

    @RequestMapping("/mood")
    public String c3(HttpServletRequest request) {

        return "mood";
    }

    @RequestMapping("/article")
    public String c4(HttpServletRequest request, Model model) {
        userservice.getArt(request, "杂谈", 5, model);
        return "article";
    }

    @RequestMapping("article/more")
    public String articleMore(HttpServletRequest request, Model model) {
        userservice.getArt(request, request.getParameter("type"), Integer.valueOf(request.getParameter("num")), model);
        return "article::div1";
    }

    @RequestMapping("/board")
    public String c5(HttpServletRequest request, Model model) {
        userservice.getSomeReply(3, model, "admin");
        return "board";
    }

    @RequestMapping("board/reply")
    public String boardReply(HttpServletRequest request, Model model) throws Exception {
        user user = (user) request.getSession().getAttribute("user");
        if (user == null) {
            return "board::ul1";
        }
        userservice.setReply(request, request.getParameter("content"), request.getParameter("addr"), null, request.getParameter("in"));
        userservice.getSomeReply(Integer.valueOf(request.getParameter("item")), model, "admin");
        return "board::ul1";
    }

    @RequestMapping("board/more")
    public String boardMore(HttpServletRequest request, Model model) {
        userservice.getSomeReply(Integer.valueOf(request.getParameter("item")), model, "admin");
        return "board::ul1";
    }

    @RequestMapping("/publish")
    public String c8(HttpServletRequest request, Model model) {
        if (checkUser(request)) {
            return "publish";
        }
        return c1(request, model);
    }

    @RequestMapping("/article_detail")
    public String c9(HttpServletRequest request, Model model) {
        Integer id = Integer.valueOf(request.getParameter("id"));
        if (id == null) {
            return c1(request, model);
        }
        userservice.getArt(request, null, 0, model);
        userservice.getOneArticle(request, id, model);
        return "article_detail";
    }

    @RequestMapping("article_detail/reply")
    public String artMore(HttpServletRequest request, Model model) {
        user user = (user) request.getSession().getAttribute("user");
        if (user == null) {
            return "article_detail";
        }
        userservice.setReply(request, request.getParameter("content"), null, Integer.valueOf(request.getParameter("addr")), request.getParameter("in"));
        userservice.getThreeReply(Integer.valueOf(request.getParameter("addr")), model, 3);
        return "article_detail::li1";
    }

    @RequestMapping("article_detail/more")
    public String moreRe(HttpServletRequest request, Model model) {
        userservice.getThreeReply(Integer.valueOf(request.getParameter("addr")), model, Integer.valueOf(request.getParameter("size")));
        return "article_detail::li1";
    }

    public boolean checkUser(HttpServletRequest request) {
        user user = (user) request.getSession().getAttribute("user");
        if (user != null && user.getName().equals("admin")) {
            return true;
        }
        return false;
    }
}
