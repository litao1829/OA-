package org.example.mapper;

import org.example.entity.Employee;
import org.example.utils.MyBatisUtils;

public class EmployeeMapper {
    public Employee selectByEmployId(long id)
    {
        return  (Employee)MyBatisUtils.executeQuery(sqlSession ->
               sqlSession.selectOne("employeemapper.selectemployid", id));
    }
}
