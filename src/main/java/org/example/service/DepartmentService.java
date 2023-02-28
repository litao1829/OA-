package org.example.service;

import org.example.entity.Department;
import org.example.mapper.DepartmentMapper;

public class DepartmentService {
    private DepartmentMapper departmentMapper=new DepartmentMapper();
    public Department findDepartByDepartId(long id)
    {
       return departmentMapper.findByEmployId(id);
    }
}
