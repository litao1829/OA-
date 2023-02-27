package org.example.mapper;

import org.example.entity.User;
import org.example.utils.MyBatisUtils;

public class UserMapper {

    public User selectByUserName(String username)
    {
        return  (User)  MyBatisUtils.executeQuery(sqlSession ->
                sqlSession.selectOne("usermapper.selectbyusername",username));
    }
}
