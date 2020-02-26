package com.company.project.biz.service.impl;

import com.company.project.biz.entity.TransferRecord;
import com.company.project.biz.mapper.TransferRecordMapper;
import com.company.project.biz.service.TransferRecordService;
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
public class TransferRecordServiceImpl implements TransferRecordService {

    @Autowired
    private TransferRecordMapper transferRecordMapper;

    @Override
    public int insert(TransferRecord transferRecord) {
        return transferRecordMapper.insert(transferRecord);
    }

    @Override
    public int selectCount(String transactionId) {
        return transferRecordMapper.selectCount(transactionId);
    }
}
