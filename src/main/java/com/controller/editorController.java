package com.controller;

import com.baidu.ueditor.ActionEnter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Controller
public class editorController {
    @RequestMapping("/ueditor")
    public void editor(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json");
        String path = request.getSession().getServletContext().getRealPath("/");
        response.setCharacterEncoding("utf-8");
        String exec = new ActionEnter(request,path).exec();
        PrintWriter writer = response.getWriter();
        writer.write(exec);
        writer.flush();
        writer.close();
    }
}
