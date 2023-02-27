package org.example.mapper;

import org.example.entity.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeMapperTest {

    @Test
    public void te1()
    {
        EmployeeMapper employeeMapper=new EmployeeMapper();
        Employee employee = employeeMapper.selectByEmployId(4);
        System.out.println(employee);
    }

}