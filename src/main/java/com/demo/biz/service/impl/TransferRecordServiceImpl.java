package com.demo.biz.service.impl;

import com.demo.biz.entity.TransferRecord;
import com.demo.biz.mapper.TransferRecordMapper;
import com.demo.biz.service.TransferRecordService;
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
    public int selectCountByRecordNo(String recordNo) {
        return transferRecordMapper.selectCountByRecordNo(recordNo);
    }
}
