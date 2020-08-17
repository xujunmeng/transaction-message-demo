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

@Component
public class TransactionListenerImpl implements TransactionListener {

    @Autowired
    private BusinessService businessService;

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        TransferRecord transferRecord = JSON.parseObject(msg.getBody(), TransferRecord.class);
        LocalTransactionState state = LocalTransactionState.UNKNOW;

        System.out.println("executeLocalTransaction transferRecord : " + JSON.toJSONString(transferRecord));
        return state;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        String transactionId = msg.getTransactionId();
        System.out.println("checkLocalTransaction transactionId : " + transactionId);

        if (query(transactionId) > 0) {
            return LocalTransactionState.COMMIT_MESSAGE;
        }
        return LocalTransactionState.UNKNOW;
    }

    private int query(String transactionId) {
        int i = businessService.checkTransferStatus(transactionId);
        return i;
    }
}
