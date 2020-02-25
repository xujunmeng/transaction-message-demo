package com.company.project.biz.mapper;

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

    int reduceMoney(@Param("userId") Long userId, @Param("money") Long money);

}
