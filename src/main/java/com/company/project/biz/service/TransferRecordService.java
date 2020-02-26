package com.company.project.biz.service;

import com.company.project.biz.entity.TransferRecord;

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

    int selectCount(String transactionId);

}
