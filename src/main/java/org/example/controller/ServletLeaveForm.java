package org.example.controller;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lombok.SneakyThrows;
import org.example.entity.LeaveForm;
import org.example.service.LeaveformService;
import org.example.service.exception.LeaveFormException;
import org.example.utils.ResponseUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ServletLeaveForm", value = "/api/leave/*")
public class ServletLeaveForm extends HttpServlet {
    LeaveformService leaveformService=null;
    @Override
    public void init() throws ServletException {
        leaveformService=new LeaveformService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        //规矩请求地址的不同执行相对应的方法
        String url=request.getRequestURI();
        String method=url.substring(url.lastIndexOf("/")+1);
        switch (method){
            case "create" ->{
                this.createLeaveForm(request,response);
            }
            case "list"->{
                this.list(request,response);
            }
            case "audit"->{
                this.audit(request,response);
            }
            default -> System.out.println("请求错误");
        }
    }

    @SneakyThrows
    public void createLeaveForm(HttpServletRequest request, HttpServletResponse response)
    {
        String eid = request.getParameter("eid");
        String formType = request.getParameter("formType");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String reason = request.getParameter("reason");
        LeaveForm form= LeaveForm.builder().
                employeeId(Long.parseLong(eid))
                .formType(Integer.parseInt(formType))
                .startTime(new Date(Long.parseLong(startTime)))
                .endTime(new Date(Long.parseLong(endTime)))
                .reason(reason)
                .createTime(new Date())
                .build();
        ResponseUtils responseUtils;
        try {
            leaveformService.createLeaveform(form);
            responseUtils = new ResponseUtils();
        }catch (LeaveFormException e){
            e.printStackTrace();
            responseUtils=new ResponseUtils(e.getClass().getSimpleName(),e.getMessage());
        }
        response.getWriter().println(JSON.toJSONString(responseUtils));
    }

    public void list(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String employeeid = request.getParameter("eid");
        ResponseUtils res=null;
        try {
            List<Map<String, Object>> leaveFormList =
                    leaveformService.getLeaveFormList("process", Long.parseLong(employeeid));
            res=new ResponseUtils().put("list",leaveFormList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            res=new ResponseUtils(e.getClass().getSimpleName(),e.getMessage());
        }
        response.getWriter().println(JSON.toJSONString(res));
    }

    public void audit(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String employeeid = request.getParameter("eid");
        String formId= request.getParameter("formId");
        String result = request.getParameter("result");
        String reason = request.getParameter("reason");
        ResponseUtils res;
       try {
           leaveformService.audit(Long.parseLong(formId),Long.parseLong(employeeid),result,reason);
            res=new ResponseUtils();
       }catch (Exception e)
       {
           e.printStackTrace();
           res=new ResponseUtils(e.getClass().getSimpleName(),e.getMessage());
       }
       response.getWriter().println(JSON.toJSONString(res));
    }
}
