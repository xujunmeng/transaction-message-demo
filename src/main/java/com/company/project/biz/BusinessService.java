package com.company.project.biz;

import com.company.project.biz.entity.TransferRecord;
import com.company.project.biz.service.TransferRecordService;
import com.company.project.biz.service.UserService;
import com.company.project.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: chenyin
 * @date: 2019-05-10 17:37
 */
@Service
public class BusinessService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransferRecordService transferRecordService;

    /**
     * 转账操作 A扣钱，同时新增转账明细
     *
     * @param fromUserId    转账人id
     * @param toUserId      被转账人id
     * @param changeMoney   转账金额
     * @param businessNo    单次转账唯一业务标识
     * @param transactionId 事务消息事务id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean doTransfer(Long fromUserId, Long toUserId, Long changeMoney, String businessNo, String transactionId) throws Exception {
        //插入转账记录明细 businessNo加唯一建 做去重操作 防止消息重试发送 导致本地事务多次执行 重复扣钱
        //转账记录中 记录 消息事务transactionId 用于后续状态回查
        TransferRecord transferRecord = new TransferRecord();
        transferRecord.setFromUserId(fromUserId);
        transferRecord.setChangeMoney(changeMoney);
        transferRecord.setTransactionId(transactionId);
        transferRecord.setToUserId(toUserId);
        transferRecord.setRecordNo(businessNo);

        transferRecordService.insert(transferRecord);

        //执行A扣钱操作
        //update user set money = money - #{money} where id = #{userId} and money >= #{money}
        int result = userService.reduceMoney(fromUserId, changeMoney);
        if (result <= 0) {
            throw new BizException("账户余额不足");
        }
        System.out.println("转账成功,fromUserId:"+fromUserId+",toUserId:"+toUserId+",money:"+changeMoney);
        return true;
    }
    /**
     * 检查本地扣钱事务执行状态
     *
     * @param transactionId
     * @return
     */
    public boolean checkTransferStatus(String transactionId) {
        //根据transactionId查询转账记录 有转账记录 标识本地事务执行成功 即A扣钱成功
        int count = transferRecordService.selectCount(transactionId);
        return count > 0;

    }

}
