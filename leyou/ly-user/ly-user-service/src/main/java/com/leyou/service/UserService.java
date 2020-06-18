package com.leyou.service;

import com.leyou.dao.UserMapper;
import com.leyou.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public Boolean check(String data, Integer type) {
        Boolean result = false;
        User user = new User();
        //判断校谁type 1：用户名 2：手机
        if(type==1){
            user.setUsername(data);
        }else if(type==2){
            user.setPhone(data);
        }
        //根据校验内容去数据库查询
        User user1 = userMapper.selectOne(user);
        if (user1==null){
            return true;
        }
        //用户名存在 false true 手机号存在false true

        return result;
    }
}
