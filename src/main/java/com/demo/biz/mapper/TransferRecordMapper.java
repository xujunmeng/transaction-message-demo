package com.demo.biz.mapper;

import com.demo.biz.entity.TransferRecord;
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

    int selectCountByTransactionId(@Param("transactionId") String transactionId);

    int selectCountByRecordNo(@Param("recordNo") String recordNo);
}
