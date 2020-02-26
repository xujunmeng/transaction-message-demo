package com.demo.biz.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *  相当于消息记录
 * </p>
 *
 * @author chenyin
 * @since 2019-05-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRecord {

    private Long id;

    /**
     * 转账人id
     */
    private Long fromUserId;

    /**
     * 转账金额
     */
    private Long changeMoney;

    /**
     * 消息事务id
     */
    private String transactionId;

    /**
     * 被转账人id
     */
    private Long toUserId;

    /**
     * 转账流水编号
     */
    private String recordNo;


}
