package org.example.controller;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.entity.User;
import org.example.service.UserService;
import org.example.service.exception.LoginException;
import org.example.utils.ResponseUtils;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/api/LoginServlet")
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService=new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ResponseUtils res;
        try {
            User user = userService.login(username, password);
            user.setPassword(null);
            user.setSalt(null);
            res=new ResponseUtils().put("user",user);
        }catch (LoginException e)
        {
            e.printStackTrace();
            res=new ResponseUtils(e.getClass().getSimpleName(),e.getMessage());
        }
        response.getWriter().println(JSON.toJSONString(res));
    }
}
