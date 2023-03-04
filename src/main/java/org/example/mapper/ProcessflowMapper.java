package org.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.entity.LeaveForm;
import org.example.entity.ProcessFlow;

import java.util.List;
import java.util.Map;

public interface ProcessflowMapper {
    int insertProcess(ProcessFlow processFlow);
    void update(ProcessFlow processFlow);
    List<ProcessFlow> selectByFormId(long id);

    //查询等待审批
    List<Map<String, Object>> selectByParams(@Param("state") String state, @Param("operatorId") Long operatorId);
}
