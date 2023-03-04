package org.example.mapper;

import org.example.entity.Employee;
import org.example.utils.MyBatisUtils;

import java.util.List;
import java.util.Map;

public class EmployeeMapper {
    public Employee selectByEmployId(long id)
    {
        return  (Employee)MyBatisUtils.executeQuery(sqlSession ->
               sqlSession.selectOne("employeemapper.selectemployid", id));
    }

    public List<Employee> selectByParams(Map<String,Object> map){
        return (List<Employee>)MyBatisUtils.executeQuery(sqlSession ->
                sqlSession.selectList("employeemapper.selectlead",map));
    }
}
