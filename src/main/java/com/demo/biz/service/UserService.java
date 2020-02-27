package com.demo.biz.service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenyin
 * @since 2019-05-10
 */
public interface UserService {

    int reduceMoney(Long fromUserId, Long changeMoney);

    int addMoney(Long toUserId, Long changeMoney);
}
