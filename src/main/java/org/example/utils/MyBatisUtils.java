package org.example.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Function;

public class MyBatisUtils {
    private static SqlSessionFactory sqlSessionFactory;
    static {
        Reader reader;
        try {
            reader= Resources.getResourceAsReader("mybatis.xml");
            sqlSessionFactory=new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //查询操作
    public static Object executeQuery(Function<SqlSession,Object> fun)
    {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        Object o = fun.apply(sqlSession);
        sqlSession.close();
        return o;
    }

    public static Object executeUpdate(Function<SqlSession,Object>fun)
    {
        SqlSession sqlSession=null;
        try {
            sqlSession=sqlSessionFactory.openSession(false);
            //apply(T t)输入参数对象t,并执行计算t对象的操作
            Object o = fun.apply(sqlSession);

            //手动提交事务
            sqlSession.commit();
            return o;
        }catch (Exception e)
        {
            sqlSession.rollback();
            throw e;
        }finally {
            sqlSession.close();
        }
    }

}
