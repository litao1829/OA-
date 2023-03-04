package org.example.service;

import org.example.entity.LeaveForm;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LeaveformServiceTest {
    private LeaveformService leaveformService=new LeaveformService();
    @Test
    public void createLeaveForm1() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMddHH");
        LeaveForm form = new LeaveForm();
        form.setEmployeeId(6L);
        form.setStartTime(sdf.parse("2023030608" ));
        form.setEndTime(sdf.parse("2023040108" ));
        form.setFormType(1);
        form.setReason("市场部员工请假单(72小时以上)");
        form.setCreateTime(new Date());
        LeaveForm savedForm = leaveformService.createLeaveform(form);
        System.out.println(savedForm);
    }

    @Test
    void getLeaveFormList() {
        LeaveformService service=new LeaveformService();
        List<Map<String, Object>> process = service.getLeaveFormList("process", 6L);
        Iterator<Map<String, Object>> iterator = process.iterator();
        while (iterator.hasNext()) {
            LinkedHashMap<String,Object> map=(LinkedHashMap<String,Object>)iterator.next();
            for(String s:map.keySet())
            {
                System.out.println(map.get(s));
            }
        }
        }
    }
