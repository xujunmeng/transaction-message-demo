package com.demo.biz.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenyin
 * @since 2019-05-10
 */
public interface UserMapper {

    int reduceMoney(@Param("fromUserId") Long fromUserId, @Param("changeMoney") Long changeMoney);

    int addMoney(@Param("toUserId") Long toUserId, @Param("changeMoney") Long changeMoney);
}
