package org.example.service;

import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.service.exception.LoginException;
import org.example.utils.MD5Utils;

public class UserService {
    private UserMapper userMapper=new UserMapper();


    //用户登录
    public User login(String username,String password)
    {
        User user = userMapper.selectByUserName(username);
        if(user==null)
        {
            throw new LoginException("用户名不存在");
        }
        else
        {
            //对password进行MD5和salt加密
            String s = MD5Utils.md5Digest(password, user.getSalt());
            if(!s.equals(user.getPassword()))
            {
                throw new LoginException("密码错误");
            }

            return user;
        }
    }

    public static void main(String[] args) {
        UserService userService=new UserService();
        User login = userService.login("t1", "test");
        System.out.println(login);
//        String test = MD5Utils.md5Digest("test", 188);
//        System.out.println(test);
    }
}
