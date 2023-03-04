package org.example.mapper;

import org.example.entity.LeaveForm;
import org.example.entity.Notice;
import org.example.service.LeaveformService;
import org.example.utils.MyBatisUtils;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import static org.junit.jupiter.api.Assertions.*;

class LeaveformMapperTest {
    @Test
    public void insert1() throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LeaveForm leaveForm=new LeaveForm();
        leaveForm.setEmployeeId(4);
        leaveForm.setFormType(2);
        leaveForm.setStartTime(format.parse("2022-03-03 11:28:00"));
        leaveForm.setEndTime(format.parse("2022-03-06 11:28:00"));
        leaveForm.setReason("aaa");
        leaveForm.setCreateTime(format.parse("2022-03-03 11:28:00"));
        leaveForm.setState("processing");
        LeaveformService service=new LeaveformService();
        Long in = service.inserinto(leaveForm);
        System.out.println("单号"+in);
        LeaveForm leaveform = service.getLeaveform(in);
        System.out.println(leaveform);
    }

    @Test
    void selectById() {
       LeaveForm f=(LeaveForm) MyBatisUtils.executeQuery(sqlSession -> {
            LeaveformMapper mapper = sqlSession.getMapper(LeaveformMapper.class);
            return mapper.selectById(2L);

        });
        System.out.println(f);
    }

    @Test
    void update() {
        MyBatisUtils.executeUpdate(sqlSession -> {
            LeaveformMapper mapper = sqlSession.getMapper(LeaveformMapper.class);
            LeaveForm form = mapper.selectById(3l);
            form.setReason("uuuuuuuuuuuuuuuuuuuuu");
            form.setState("processing");
            mapper.update(form);
            return null;
        });
    }
}