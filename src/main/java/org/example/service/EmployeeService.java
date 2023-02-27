package org.example.service;

import org.apache.ibatis.annotations.Select;
import org.example.entity.Employee;
import org.example.mapper.EmployeeMapper;

public class EmployeeService {
    private EmployeeMapper employeeMapper=new EmployeeMapper();
    public Employee selcetByEid(long id)
    {
        return employeeMapper.selectByEmployId(id);
    }
}
