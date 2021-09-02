package com.book.web;

import com.book.pojo.User;
import com.book.service.UserService;
import com.book.service.imple.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LVFASEN
 * @create 2021-08-16 10:22
 */
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User login = userService.login(new User(null, username, password, null));

        if(login != null){
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req,resp);
        }else{
            //把错误信息回显
            req.setAttribute("msg","用户名或密码错误！");
            req.setAttribute("username",username);
            req.setAttribute("password",password);
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
        }

    }
}
