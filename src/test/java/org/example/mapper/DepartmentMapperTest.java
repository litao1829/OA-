package org.example.mapper;

import org.example.entity.Department;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentMapperTest {
    @Test
    public void testDepartment1()
    {
        DepartmentMapper departmentMapper=new DepartmentMapper();
        Department byemployId = departmentMapper.findByEmployId(1L);
        System.out.println(byemployId);
    }

}