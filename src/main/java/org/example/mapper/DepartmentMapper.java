package org.example.mapper;

import org.example.entity.Department;
import org.example.utils.MyBatisUtils;

public class DepartmentMapper {
    public Department findByEmployId(long id)
    {
       return (Department) MyBatisUtils.executeQuery(sqlSession -> sqlSession.selectOne("department.seletdepartment",id));
    }
}
