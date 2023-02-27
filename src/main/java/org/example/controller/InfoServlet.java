package org.example.controller;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.entity.Employee;
import org.example.service.EmployeeService;
import org.example.utils.ResponseUtils;

import java.io.IOException;

@WebServlet(name = "InfoServlet", value = "/api/InfoServlet")
public class InfoServlet extends HttpServlet {

    private EmployeeService service;

    @Override
    public void init() throws ServletException {
        super.init();
        service=new EmployeeService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eid = request.getParameter("eid");
        Employee employee = service.selcetByEid(Long.parseLong(eid));
        ResponseUtils responseUtils=new ResponseUtils();
        responseUtils.put("employee",employee);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(JSON.toJSONString(responseUtils));
    }
}
