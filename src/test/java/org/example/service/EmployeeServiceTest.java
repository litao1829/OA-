package org.example.service;

import org.example.entity.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {
    @Test
    public void testleader()
    {
        EmployeeService service=new EmployeeService();
        Employee employee = service.selectLeader(10L);
        System.out.println(employee);
    }
}