package org.example.controller;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.entity.Notice;
import org.example.service.NoticeService;
import org.example.utils.ResponseUtils;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletNotice", value = "/api/notice/ServletNotice")
public class ServletNotice extends HttpServlet {
    private final NoticeService noticeService=new NoticeService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeId=request.getParameter("eid");
        ResponseUtils res;

        try {
            List<Notice> noticeList = noticeService.getNoticeList(Long.parseLong(employeeId));
            res=new ResponseUtils().put("list",noticeList);
        }
        catch (Exception e){
            e.printStackTrace();
            res=new ResponseUtils(e.getClass().getSimpleName(),e.getMessage());
        }
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(JSON.toJSONString(res));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
