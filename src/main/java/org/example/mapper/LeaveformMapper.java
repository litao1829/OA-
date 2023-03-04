package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.entity.LeaveForm;
import org.example.utils.MyBatisUtils;

public interface LeaveformMapper {

    Long into(LeaveForm leaveForm);
    LeaveForm selectById(Long i);
    void update(LeaveForm form);


}
