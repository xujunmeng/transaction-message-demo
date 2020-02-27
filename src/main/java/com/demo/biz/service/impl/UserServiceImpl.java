package com.demo.biz.service.impl;

import com.demo.biz.mapper.UserMapper;
import com.demo.biz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenyin
 * @since 2019-05-10
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int reduceMoney(Long fromUserId, Long changeMoney) {
        return userMapper.reduceMoney(fromUserId, changeMoney);
    }

    @Override
    public int addMoney(Long toUserId, Long changeMoney) {
        return userMapper.addMoney(toUserId, changeMoney);
    }
}
