package org.example.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyBatisUtilsTest {
    @Test
    public void  testCase1()
    {
        String s =(String) MyBatisUtils.executeQuery(sqlSession -> sqlSession.selectOne("test.sample"));
        System.out.println(s);
    }
}