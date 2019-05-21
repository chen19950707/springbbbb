package com.service;

import com.dao.UserDao;
import com.pojo.*;
import com.util.codeUtil;
import com.util.mailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Service
public class userservice {
    @Autowired
    UserDao userDao;
    Integer id;
    //获取某一相册
    public Map getPhoto(Integer id){
        photos photoo = userDao.getPhotoo(id);
        List<photo> photo = userDao.getPhoto(id);
        Map<String,Object> map = new HashMap<>();
        List inlist =new ArrayList<photo>();
        for (com.pojo.photo photo1 : photo) {
            Map<String,Object> in = new HashMap<>();
            in.put("src",photo1.getUrl());
            in.put("alt",photoo.getTittle());
            inlist.add(in);
        }
        map.put("data",inlist);
        map.put("title",photoo.getTittle());
        return map;
    }
    //获取相册内容
    public void getPhotos(HttpServletRequest request,Model model,Integer num){
        List<photos> photos = userDao.getPhotos(num);
        model.addAttribute("album",photos);
    }
    //上传相册
    public void upPhoto(HttpServletRequest request, MultipartFile[] files) throws Exception {
        String path = ResourceUtils.getURL("classpath:static/photo").getPath();
        File file1 = new File(path);
        if(!file1.exists()){
            file1.mkdirs();
        }
        String tittle = request.getParameter("tittle");
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String format1 = format.format(date);
        photos photos = new photos();
        photos.setTime(format1);
        photos.setTittle(tittle);
        photos ptittles = userDao.getPtittles(photos);
        if(ptittles==null){
            userDao.savePtittle(photos);
            id = photos.getId();
        }else {
            return;
        }
        for (MultipartFile file : files) {
            photo photo = new photo();
            String name = UUID.randomUUID()+file.getOriginalFilename();
            String url = "static/photo/"+name;
            photo.setUrl(url);
            photo.setTid(id);
            file.transferTo(new File(file1 + File.separator + name));
            userDao.savePhoto(photo);
        }
    }
    //更多文章
    public List<article> getMore(HttpServletRequest request){
        String img = "src=\"";
        Integer num =Integer.valueOf(request.getParameter("num"));
        List<article> article = userDao.getArticle(null, num);
        for (article article1 : article) {
            Integer index=article1.getContent().indexOf(img);
            if(index>=0){
                int i = article1.getContent().indexOf("\"", index+5);
                article1.setImg(article1.getContent().substring(index+5,i));
            }
            String s = article1.getContent();
            /** 删除普通标签  */
            s = s.replaceAll("<(S*?)[^>]*>.*?|<.*? />", "");
            /** 删除转义字符 */
            s = s.replaceAll("&.{2,6}?;", "");
            article1.setWord(s);
        }
        return article;
    }
    //进入留言页面,获取回复
    public void getSomeReply(Integer item,Model model,String addr){
        List<reply> boardReply = userDao.getReply(addr,item);
        model.addAttribute("boardReply",boardReply);
    }
    //发表回复
    public Integer setReply(HttpServletRequest request,String content,String addr,Integer reid,String in ){
        if(!"admin".equals(addr) && !"inReply".equals(in)){
            article article = userDao.getOneArticle(reid);
            Integer rcount =Integer.valueOf(article.getRcount());
            rcount++;
            userDao.setArticleReply(rcount,reid);
        }
        user user =(user) request.getSession().getAttribute("user");
        String master = user.getName();
        String masterimg = user.getImg();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String format = sdf.format(date);
        reply reply = new reply();
        reply.setAddr(addr);
        reply.setContent(content);
        reply.setMaster(master);
        reply.setMasterImg(masterimg);
        reply.setTime(format);
        reply.setReid(reid);
        return userDao.setReply(reply);
    }
    //获取某篇文章,并实现点击量+1,并获取文章的回复
    public void getOneArticle(HttpServletRequest request,Integer id,Model model){
        article pre = userDao.getPre(id);
        article article = userDao.getOneArticle(id);
        article next = userDao.getNext(id);
        Integer pcount =Integer.valueOf(article.getPcount());
        pcount++;
        userDao.setPoint(id,pcount);
        List<reply> reply = userDao.getArticleReply(id,3);
        article.setPcount(pcount.toString());
        model.addAttribute("reply",reply);
        model.addAttribute("pre",pre);
        model.addAttribute("oneArticle",article);
        model.addAttribute("next",next);
    }
    //获取文章回复
    public void getThreeReply(Integer id,Model model,Integer num){
        List<reply> reply = userDao.getArticleReply(id,num);
        model.addAttribute("reply",reply);
    }
    //发表文章
    public Integer saveArticle(HttpServletRequest request,HttpServletResponse response){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String format = sdf.format(date);
        return userDao.saveArticle(request.getParameter("tittle"), request.getParameter("content"), format,request.getParameter("type"));
    }
    //注册成功,存入用户信息
    public Integer saveUser(HttpServletRequest request,HttpServletResponse response){
        user newUser = new user();
        newUser.setName(request.getParameter("name"));
        newUser.setPwd(request.getParameter("pwd"));
        newUser.setMail(request.getParameter("mail"));
        return userDao.saveUser(newUser);
    }
    //验证用户是否已注册
    public String checkname(HttpServletRequest request,HttpServletResponse response){
        user people = new user();
        people.setName(request.getParameter("name"));
        return userDao.checkName(people);
    }
    //验证邮箱验证码
    public String mailcode(HttpServletRequest request,HttpServletResponse response){
        String code = request.getParameter("code");
        String mcode =(String) request.getSession().getAttribute("code");
        if(code.equals(mcode)){
            return "true";
        }else{
            return "false";
        }
    }
    //发送邮箱验证码
    public String sendCode(HttpServletRequest request,HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String mail = request.getParameter("mail");
        System.out.println(mail);
        user muser = new user();
        muser.setMail(mail);
        user user = userDao.getUser(muser);
        if(user==null){
            request.getSession().setAttribute("time",60);
            String code = codeUtil.getCode();
            request.getSession().setAttribute("code",code);
            new Thread(new mailUtil(mail,code)).start();
            return "true";
        }else{
            return "false";
        }
    }
    //验证图片验证码
    public String checkcode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String code = request.getParameter("code");
        String checkcode_session = (String) request.getSession().getAttribute("checkcode_session");
        if(code.equals(checkcode_session)){
            return "true";
        }else{
            return "false";
        }
    }
    //登录验证用户信息
    public String checkuser(HttpServletRequest request,HttpServletResponse response,Model model) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        String save = request.getParameter("save");
        user quser = new user();
        quser.setName(name);
        quser.setPwd(pwd);
        user user = userDao.getUser(quser);
        if(user!=null){
            if(save.equals("true")){
                System.out.println("添加cookie");
                Cookie cookie=new Cookie("loginInfo",user.getName()+"+"+user.getPwd());
                cookie.setMaxAge(60*60*24*7);
                cookie.setPath("/");
                response.addCookie(cookie);
            }else{
                Cookie cookie=new Cookie("loginInfo",user.getName()+"+"+user.getPwd());
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            request.getSession().setAttribute("user",user);
            getArt(request,null,10,model);
            return "true";
        }
        return "false";
    }
    //获取最新文章,回复最多(热门推荐),点击最多
    public void getArt(HttpServletRequest request, String type, Integer num, Model model){
        String img = "src=\"";
        if(num>0){
            List<article> article = userDao.getArticle(type,num);
            for (article article1 : article) {
                Integer index=article1.getContent().indexOf(img);
                if(index>=0){
                    int i = article1.getContent().indexOf("\"", index+5);
                    article1.setImg(article1.getContent().substring(index+5,i));
                }
                String s = article1.getContent();
                /** 删除普通标签  */
                s = s.replaceAll("<(S*?)[^>]*>.*?|<.*? />", "");
                /** 删除转义字符 */
                s = s.replaceAll("&.{2,6}?;", "");
                article1.setWord(s);
            }
            model.addAttribute("article",article);
        }

        List<article> hot = userDao.getHot();
        List<article> point = userDao.getPoint();
        request.getSession().setAttribute("point",point);
        request.getSession().setAttribute("hotArticle",hot);
        //request.getSession().setAttribute("article",article);
    }

    //验证码图片
    public void imgcode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<String> words = new ArrayList<String>();
        String path = ResourceUtils.getURL("classpath:static/new_words.txt").getPath();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(line);
        }
        reader.close();
        int width = 120;
        int height = 30;

        // 步骤一 绘制一张内存中图片
        BufferedImage bufferedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        // 步骤二 图片绘制背景颜色 ---通过绘图对象
        Graphics graphics = bufferedImage.getGraphics();// 得到画图对象 --- 画笔
        // 绘制任何图形之前 都必须指定一个颜色
        graphics.setColor(getRandColor(200, 250));
        graphics.fillRect(0, 0, width, height);

        // 步骤三 绘制边框
        graphics.setColor(Color.WHITE);
        graphics.drawRect(0, 0, width - 1, height - 1);

        // 步骤四 四个随机数字
        Graphics2D graphics2d = (Graphics2D) graphics;
        // 设置输出字体
        graphics2d.setFont(new Font("宋体", Font.BOLD, 18));

        Random random = new Random();// 生成随机数
        int index = random.nextInt(words.size());
        String word = words.get(index);// 获得成语

        // 定义x坐标
        int x = 10;
        for (int i = 0; i < word.length(); i++) {
            // 随机颜色
            graphics2d.setColor(new Color(20 + random.nextInt(110), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));
            // 旋转 -30 --- 30度
            int jiaodu = random.nextInt(60) - 30;
            // 换算弧度
            double theta = jiaodu * Math.PI / 180;

            // 获得字母数字
            char c = word.charAt(i);

            // 将c 输出到图片
            graphics2d.rotate(theta, x, 20);
            graphics2d.drawString(String.valueOf(c), x, 20);
            graphics2d.rotate(-theta, x, 20);
            x += 30;
        }

        // 将验证码内容保存session
        request.getSession().setAttribute("checkcode_session", word);

        // 步骤五 绘制干扰线
        graphics.setColor(getRandColor(160, 200));
        int x1;
        int x2;
        int y1;
        int y2;
        for (int i = 0; i < 30; i++) {
            x1 = random.nextInt(width);
            x2 = random.nextInt(12);
            y1 = random.nextInt(height);
            y2 = random.nextInt(12);
            graphics.drawLine(x1, y1, x1 + x2, x2 + y2);
        }

        // 将上面图片输出到浏览器 ImageIO
        graphics.dispose();// 释放资源

        //将图片写到response.getOutputStream()中
        ImageIO.write(bufferedImage, "jpg", response.getOutputStream());

    }


    private Color getRandColor(int fc, int bc) {
        // 取其随机颜色
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
