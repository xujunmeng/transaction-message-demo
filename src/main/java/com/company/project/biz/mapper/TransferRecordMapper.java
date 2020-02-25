package com.company.project.biz.mapper;

import com.company.project.biz.entity.TransferRecord;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenyin
 * @since 2019-05-10
 */
public interface TransferRecordMapper {

    int insert(TransferRecord transferRecord);

    int selectCount(@Param("transactionId") String transactionId);
}
