package org.example.service;

import org.apache.ibatis.annotations.Select;
import org.example.entity.Employee;
import org.example.mapper.EmployeeMapper;

import java.util.HashMap;
import java.util.Map;

public class EmployeeService {
    private EmployeeMapper employeeMapper=new EmployeeMapper();
    public Employee selcetByEid(long id)
    {
        return employeeMapper.selectByEmployId(id);
    }

    public Employee selectLeader(Long employeeid)
    {
        //通过员工id找到员工对象
        Employee employee = employeeMapper.selectByEmployId(employeeid);
        Map<String,Object> map=new HashMap<>();
        Employee leader=null;
        //如果是普通员工找部门经理
        if(employee.getLevel()<7)
        {
            map.put("departmentId",employee.getDepartmentId());
            map.put("level",7);
            leader= employeeMapper.selectByParams(map).get(0);
        }
        //如果是部门经理找总经理
        else if(employee.getLevel()==7)
        {
            map.put("level",8);
            leader= employeeMapper.selectByParams(map).get(0);
        }
        else
        {
            leader=employee;
        }
        return leader;
    }
}
