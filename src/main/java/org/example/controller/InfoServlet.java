package org.example.controller;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.entity.Department;
import org.example.entity.Employee;
import org.example.service.DepartmentService;
import org.example.service.EmployeeService;
import org.example.service.NodeService;
import org.example.utils.ResponseUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "InfoServlet", value = "/api/InfoServlet")
public class InfoServlet extends HttpServlet {

    private EmployeeService service;
    private DepartmentService departservice;
    private NodeService nodeservice;

    @Override
    public void init() throws ServletException {
        super.init();
        service=new EmployeeService();
        departservice=new DepartmentService();
        nodeservice=new NodeService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eid = request.getParameter("eid");
        String uid = request.getParameter("uid");
        System.out.println("eid:"+eid+",uid:"+uid);
        //通过eid和uid找出此员工的部门和该有的模块功能
        Employee employee = service.selcetByEid(Long.parseLong(eid));
        //通过部门id确定是哪个部门
        Department department = departservice.findDepartByDepartId(employee.getDepartmentId());
        //找到模块和功能
        List<HashMap<String, Object>> allNode = nodeservice.findAllNode(Long.parseLong(uid));


        ResponseUtils responseUtils=new ResponseUtils();
        responseUtils.put("employee",employee);
        responseUtils.put("department",department);
        responseUtils.put("lists",allNode);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(JSON.toJSONString(responseUtils));
    }
}
