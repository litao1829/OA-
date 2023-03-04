package org.example.service;

import org.example.entity.Employee;
import org.example.entity.LeaveForm;
import org.example.entity.Notice;
import org.example.entity.ProcessFlow;
import org.example.mapper.EmployeeMapper;
import org.example.mapper.LeaveformMapper;
import org.example.mapper.NoticeMapper;
import org.example.mapper.ProcessflowMapper;
import org.example.service.exception.LeaveFormException;
import org.example.utils.DateUtils;
import org.example.utils.MyBatisUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LeaveformService {
    private final  EmployeeService empserivce=new EmployeeService();
    public Long inserinto(LeaveForm leaveForm){
        return (Long) MyBatisUtils.executeUpdate(sqlSession -> {
            LeaveformMapper mapper = sqlSession.getMapper(LeaveformMapper.class);
            mapper.into(leaveForm);
            return leaveForm.getFormId();
        });
    }

    public LeaveForm getLeaveform(Long id)
    {
        return (LeaveForm)MyBatisUtils.executeQuery(sqlSession -> {
            LeaveformMapper mapper=sqlSession.getMapper(LeaveformMapper.class);
             return mapper.selectById(id);
        });
    }

    //请假单的创建和请假流程的创建
    public LeaveForm createLeaveform(LeaveForm form)
    {

        //每次添加流程时都要进行通知的添加
        return (LeaveForm) MyBatisUtils.executeUpdate(sqlSession -> {
            //根据请假单中的员工号，查询员工信息
            Employee employee = empserivce.selcetByEid(form.getEmployeeId());

            //2.如果此员工就是总经理则直接通过
            if(employee.getLevel()==8)
            {
                form.setState("approved");
            }
            else
            {
                form.setState("processing");
            }
            //添加请假单
            inserinto(form);


            NoticeMapper noticemapper = sqlSession.getMapper(NoticeMapper.class);
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd-HH时");

            //处理流表中
            //员工自己提交的处理流
            ProcessflowMapper processflowMapper = sqlSession.getMapper(ProcessflowMapper.class);
            ProcessFlow processFlow=ProcessFlow.builder()
                    .formId(form.getFormId())
                    .operatorID(form.getEmployeeId())
                    .action("apply")
                    .createTime(new Date())
                    .orderNo(1)
                    .state("complete")
                    .isLast(0)
                    .build();
            processflowMapper.insertProcess(processFlow);


            //其余的处理流
            int level=employee.getLevel();
            switch (level){
                case 1,2,3,4,5,6->{
                    Employee leader = empserivce.selectLeader(employee.getEmployeeId());
                    ProcessFlow processFlow2=new ProcessFlow();
                    processFlow2.setFormId(form.getFormId());
                    processFlow2.setOperatorID(leader.getEmployeeId());
                    processFlow2.setAction("audit");
                    processFlow2.setCreateTime(new Date());
                    processFlow2.setOrderNo(2);
                    processFlow2.setState("process");


                    Long hours = DateUtils.getHours(form.getStartTime(), form.getEndTime());
                    if(hours>=72)
                    {
                        //大于72小时需要在添加一条
                        processFlow2.setIsLast(0);
                        processflowMapper.insertProcess(processFlow2);
                        Employee boss = empserivce.selectLeader(leader.getEmployeeId());
                        ProcessFlow pf3=new ProcessFlow();
                        pf3.setFormId(form.getFormId());
                        pf3.setOperatorID(boss.getEmployeeId());
                        pf3.setAction("audit");
                        pf3.setCreateTime(new Date());
                        pf3.setOrderNo(3);
                        pf3.setIsLast(1);
                        pf3.setState("ready");
                        processflowMapper.insertProcess(pf3);

                    }
                    else
                    {
                        processFlow2.setIsLast(1);
                        processflowMapper.insertProcess(processFlow2);

                    }
                    //发给申请人的通知
                    String notice1=String.format("您的请假申请[%s-%s]已提交，等待上级审批",
                            format.format(form.getStartTime()),
                            format.format(form.getEndTime()));

                            noticemapper.insert(Notice.builder()
                            .receiverId(form.getEmployeeId())
                            .content(notice1)
                            .createTime(new Date())
                            .build());


                        //发送给审批人的通知
                        String notice2=String.format("%s-%s提起请假申请[%s-%s]请尽快审批",
                            employee.getTitle(),
                            employee.getName(),
                            format.format(form.getStartTime()),
                            format.format(form.getEndTime()));

                            noticemapper.insert(Notice.builder()
                            .receiverId(leader.getEmployeeId())
                            .content(notice2)
                            .createTime(new Date())
                            .build());
                }
                case 7->{
                    //仅仅生成总经理审批
                    Employee boss = empserivce.selectLeader(employee.getEmployeeId());
                    ProcessFlow pf3=new ProcessFlow();
                    pf3.setFormId(form.getFormId());
                    pf3.setOperatorID(boss.getEmployeeId());
                    pf3.setAction("audit");
                    pf3.setCreateTime(new Date());
                    pf3.setOrderNo(2);
                    pf3.setIsLast(1);
                    pf3.setState("process");
                    processflowMapper.insertProcess(pf3);
                    //发给申请人的通知
                    String notice1=String.format("您的请假申请[%s-%s]已提交，等待上级审批",
                            format.format(form.getStartTime()),
                            format.format(form.getEndTime()));

                    noticemapper.insert(Notice.builder()
                            .receiverId(form.getEmployeeId())
                            .content(notice1)
                            .createTime(new Date())
                            .build());


                    //发送给审批人的通知
                    String notice2=String.format("%s-%s提起请假申请[%s-%s]请尽快审批",
                            employee.getTitle(),
                            employee.getName(),
                            format.format(form.getStartTime()),
                            format.format(form.getEndTime()));

                    noticemapper.insert(Notice.builder()
                            .receiverId(boss.getEmployeeId())
                            .content(notice2)
                            .createTime(new Date())
                            .build());
                }
                case 8->{
                    ProcessFlow pf3=new ProcessFlow();
                    pf3.setFormId(form.getFormId());
                    pf3.setOperatorID(employee.getEmployeeId());
                    pf3.setAction("audit");
                    pf3.setResult("approved");
                    pf3.setReason("自动审批");
                    pf3.setCreateTime(new Date());
                    pf3.setAuditTime(new Date());
                    pf3.setOrderNo(2);
                    pf3.setIsLast(1);
                    pf3.setState("complete");
                    processflowMapper.insertProcess(pf3);
                    //发给申请人的通知
                    String notice1=String.format("您的请假申请[%s-%s]已自动审批完成",
                            format.format(form.getStartTime()),
                            format.format(form.getEndTime()));

                    noticemapper.insert(Notice.builder()
                            .receiverId(form.getEmployeeId())
                            .content(notice1)
                            .createTime(new Date())
                            .build());
                }
                default -> throw  new LeaveFormException("没有此员工");
            }
            return form;
        });
    }


    //获取指定任务状态指定经办人对应的请假单列表
    public List<Map<String,Object>> getLeaveFormList(String state,Long operatorId)
    {
        return (List<Map<String, Object>>) MyBatisUtils.executeQuery(sqlSession -> {
            ProcessflowMapper mapper = sqlSession.getMapper(ProcessflowMapper.class);
            return mapper.selectByParams(state,operatorId);
        });
    }


    //审批,需要传递表单号、经办人、审批结果、审批意见
    public void audit(long formId,long operatorId,String result,String reason)
    {
        MyBatisUtils.executeUpdate(sqlSession -> {
            //先更具formId找到所有的流程记录
            ProcessflowMapper processmapper = sqlSession.getMapper(ProcessflowMapper.class);
            List<ProcessFlow> flowslists = processmapper.selectByFormId(formId);
            if(flowslists.size()==0){//如果没找到
                throw new LeaveFormException("无效的审批，没有此项流程");
            }
            //只需要获取state的值为process的记录,对flowslist进行筛选
            List<ProcessFlow> processlists = flowslists.stream().filter(p -> Objects.equals(p.getOperatorID(), operatorId)
                    && "process".equals(p.getState())).toList();
            ProcessFlow process;
            if(processlists.size()==0)//没有找到
            {
                throw new LeaveFormException("未找到任务节点");
            }
            else{//如果有记录
                //部门经理处理记录
                process=processlists.get(0);
                process.setState("complete");//不管是拒绝还是同意此条记录状态都得完成
                process.setResult(result);
                process.setReason(reason);
                process.setAuditTime(new Date());
                processmapper.update(process);
            }
                //找出当前的请假单子
                LeaveformMapper leavemapper = sqlSession.getMapper(LeaveformMapper.class);
                LeaveForm leaveForm = leavemapper.selectById(formId);

                //找出请假人
            Employee employee = empserivce.selcetByEid(leaveForm.getEmployeeId());
            //当前审批人
            Employee operator = empserivce.selcetByEid(operatorId);

            NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd-HH时");
            //如果当前的任务不是最后一个节点且申请审批通过，那么下一个节点的状态从ready变成process
                if(process.getIsLast()==1)//是最后一个节点
                {
                    leaveForm.setState(result);
                    leavemapper.update(leaveForm);
                    String strResult=null;
                    if("approved".equals(result))
                    {
                        strResult="批准";
                    }
                    else if("refused".equals(result))
                    {
                        strResult="驳回";
                    }
                    //发给申请人的通知
                    String notice1=String.format("您的请假申请[%s-%s]%s%s已%s，审批意见:%s,审批流程已结束",
                            format.format(leaveForm.getStartTime()),
                            format.format(leaveForm.getEndTime()),
                            operator.getTitle(),
                            operator.getName(),
                            strResult,
                            reason);

                    noticeMapper.insert(Notice.builder()
                            .receiverId(leaveForm.getEmployeeId())
                            .content(notice1)
                            .createTime(new Date())
                            .build());


                    //发送给审批人的通知
                    String notice2=String.format("%s-%s提起请假申请[%s-%s]您已%s,审批意见：%s，审批流程已结束",
                            employee.getTitle(),
                            employee.getName(),
                            format.format(leaveForm.getStartTime()),
                            format.format(leaveForm.getEndTime()),
                            strResult,
                            reason);

                    noticeMapper.insert(Notice.builder()
                            .receiverId(operator.getEmployeeId())
                            .content(notice2)
                            .createTime(new Date())
                            .build());

                }
                else {//不是最后一个节点
                    //找到states是ready的节点
                    List<ProcessFlow> readylist = flowslists.stream().filter(p -> "ready".equals(p.getState())).toList();
                    //如果当前任务不是最后一个节点且审批通过，那么下一个节点的状态因该从ready变成process
                    if("approved".equals(result)){
                        ProcessFlow readprocess = readylist.get(0);
                        readprocess.setState("process");
                        processmapper.update(readprocess);

                        //消息1：通知表单的提交人，部门经理已经审批通过，交由上审批
                        String notice1 = String.format("您的请假申请[%s-%s]%s%s已批准,审批意见:%s ,请继续等待上级审批",
                                format.format(leaveForm.getStartTime()),
                                format.format(leaveForm.getEndTime()),
                                operator.getTitle(),
                                operator.getName(),reason);
                        noticeMapper.insert( Notice.builder()
                                .receiverId(leaveForm.getEmployeeId())
                                .content(notice1).
                                createTime(new Date())
                                .build());

                        //消息2：通知部门经理(当前的审批人)，员工的申请单您已批准,交由上级审批
                        String notice2 = String.format("%s-%s提起请假申请[%s-%s]您已批准,审批意见:%s,申请转至上级领导继续审批",
                                employee. getTitle(),
                                employee.getName(),
                                format.format(leaveForm.getStartTime()),
                                format.format(leaveForm.getEndTime()),
                                reason);
                        noticeMapper.insert(Notice.builder()
                                .receiverId(operator.getEmployeeId())
                                .content(notice2)
                                .createTime(new Date()).build());

                        //消息3：通知总经理有审批
                        String notice3 = String.format( "%s-%s提起请假申请[%s-%s ] ,请尽快审批",
                                employee. getTitle( ),
                                employee. getName( ),
                                format.format(leaveForm.getStartTime()),
                                format.format(leaveForm.getEndTime()));

                        noticeMapper.insert( Notice.builder()
                                .receiverId(readprocess.getOperatorID())
                                .content(notice3)
                                .createTime(new Date())
                                .build());



                    }
                    else if("refused".equals(result)){//如果拒接不通过
                        //如果当前任务不是最后一个节点同时审批驳回，则后续所有的任务状态变为cancel
                        for(ProcessFlow p:readylist)
                        {
                            p.setState("cancel");
                            processmapper.update(p);
                        }
                        leaveForm.setState("refused");
                        leavemapper.update(leaveForm);


                        //消息1.通知申请人表单已被回驳回
                        String notice1 = String. format("您的请假申请[%s-%s ]%s%s已驳回,审批意见:%s ,审批流程已结束",
                                format.format(leaveForm.getStartTime( )),
                                format.format(leaveForm.getEndTime()),
                                operator.getTitle(),
                                operator.getName(),
                                reason) ;
                        noticeMapper.insert(Notice.builder()
                                .receiverId ( leaveForm.getEmployeeId())
                                .content(notice1)
                                .createTime(new Date()).build());

                        //消息2.通知经办人表单“您驳回”
                        String notice2 = String.format( "%s-%s提起请假申请[%s-%s]您已驳回,审批意见:%s ,审批流程已结束",
                                employee.getTitle( ),
                                employee.getName( ),
                                format.format(leaveForm.getStartTime()),
                                format.format( leaveForm.getEndTime()),
                                reason) ;
                        noticeMapper.insert(Notice.builder()
                                .receiverId(operator.getEmployeeId()).content( notice2)
                                .createTime(new Date())
                                .build());
                    }
                }

            return null;
        });
    }

}
