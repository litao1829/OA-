package org.example.mapper;

import org.example.entity.Employee;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeMapperTest {

    @Test
    public void te1()
    {
        EmployeeMapper employeeMapper=new EmployeeMapper();
        Employee employee = employeeMapper.selectByEmployId(4);
        System.out.println(employee);
    }
    @Test
    public void te2(){
        Map<String,Object> map=new HashMap<>();
        map.put("departmentId",2);
        map.put("level",7);
        EmployeeMapper mapper=new EmployeeMapper();
        List<Employee> employees = mapper.selectByParams(map);
        System.out.println(employees);
    }
}