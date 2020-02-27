/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.demo.biz;

import com.alibaba.fastjson.JSON;
import com.demo.biz.entity.TransferRecord;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class TransactionListenerImpl implements TransactionListener {

    private ConcurrentHashMap<String, Integer> countHashMap = new ConcurrentHashMap<>();

    private final static int MAX_COUNT = 5;

    @Autowired
    private BusinessService businessService;

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        TransferRecord transferRecord = JSON.parseObject(msg.getBody(), TransferRecord.class);
        LocalTransactionState state = LocalTransactionState.UNKNOW;
        try {
            businessService.doTransfer(transferRecord.getFromUserId(),transferRecord.getToUserId()
                    ,transferRecord.getChangeMoney(),transferRecord.getRecordNo(),msg.getTransactionId());
        } catch (Exception e) {
            System.out.println("转账失败,fromUserId:"+transferRecord.getFromUserId()+",toUserId:"+transferRecord.getToUserId()+",money:"+transferRecord.getChangeMoney());
            e.printStackTrace();
        }
        return state;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        String transactionId = msg.getTransactionId();

        if (query(transactionId) > 0) {
            return LocalTransactionState.COMMIT_MESSAGE;
        }
        return rollBackOrUnknow(transactionId);
    }

    private int query(String transactionId) {
        return businessService.checkTransferStatus(transactionId);
    }

    private LocalTransactionState rollBackOrUnknow(String transactionId) {
        Integer num = countHashMap.get(transactionId);
        if (num != null && ++num > MAX_COUNT) {
            countHashMap.remove(transactionId);
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }

        if (num == null) {
            num = 1;
        }
        countHashMap.put(transactionId, num);

        return LocalTransactionState.UNKNOW;
    }
}
