package com.book.web;

import com.book.dao.impl.UserDaoImpl;
import com.book.pojo.Order;
import com.book.pojo.User;
import com.book.service.UserService;
import com.book.service.imple.UserServiceImpl;
import com.book.utils.WebUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 * @author LVFASEN
 * @create 2021-08-22 10:19
 */
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();

    /**
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    public void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取session中验证码
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        //删除session中验证码
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");

        User user = WebUtil.copyParamToBean(req.getParameterMap(),new User());

        if(token != null && token.equalsIgnoreCase(code)){
            if(userService.existsUsername(username)){
                req.setAttribute("msg","用户名已存在！");
                req.setAttribute("username",username);
                req.setAttribute("email",email);

                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
            }else{


                userService.registUser(user);
//                req.getSession().setAttribute("user",user);
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req,resp);
            }
        }else{
            req.setAttribute("msg","验证码错误！");
            req.setAttribute("username",username);
            req.setAttribute("email",email);

            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
        }
    }

    /**
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    public void login(HttpServletRequest req,HttpServletResponse resp)throws ServletException, IOException{
        String username = req.getParameter("username");
        String password = req.getParameter("password");


        User login = userService.login(new User(null, username, password, null));

        if(login != null){
            //保存用户登陆之后的信息到session域中
            req.getSession().setAttribute("user",login);
//            //创建cookie对象
//            Cookie cookie = new Cookie("username",username);
//            cookie.setMaxAge(60*60*24*7);
//            resp.addCookie(cookie);
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req,resp);
        }else{
            req.setAttribute("msg","用户名或密码错误！");
            req.setAttribute("username",username);
            req.setAttribute("password",password);

            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
        }
    }
    public void logout(HttpServletRequest req,HttpServletResponse resp)throws ServletException, IOException {
        //销毁session中用户信息
        req.getSession().invalidate();
        //重定向到首页
        resp.sendRedirect(req.getContextPath());
    }


    protected void ajaxExistsUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数username
        String username = req.getParameter("username");
        System.out.println(req.getParameter("username"));
        //调用userService.existsUsername()方法判断，用户名是否已经存在
        boolean existsUsername = userService.existsUsername(username);
        //将返回的结果封装成map用于返回
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("existsUsername",existsUsername);

        //将map转变成json对象
        Gson gson = new Gson();
        String resultMapJson = gson.toJson(resultMap);

        resp.getWriter().write(resultMapJson);
    }
    //    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String action = req.getParameter("action");
//        try {
//            Method declaredMethod = this.getClass().getDeclaredMethod(action,HttpServletRequest.class,HttpServletResponse.class);
//
//            declaredMethod.invoke(this,req,resp);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if("login".equalsIgnoreCase(action)){
//                login(req,resp);
//        }else if("regist".equalsIgnoreCase(action)){
//                regist(req,resp);
//        }
//    }
}
