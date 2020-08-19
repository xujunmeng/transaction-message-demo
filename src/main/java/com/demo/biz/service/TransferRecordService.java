package com.demo.biz.service;

import com.demo.biz.entity.TransferRecord;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenyin
 * @since 2019-05-10
 */
public interface TransferRecordService {

    int insert(TransferRecord transferRecord);

    int selectCountByRecordNo(String recordNo);
}
