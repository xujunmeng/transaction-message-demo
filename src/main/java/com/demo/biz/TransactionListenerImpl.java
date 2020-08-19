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

        //executeLocalTransaction 该方法的职责是记录事务消息的本地事务状态，
        //例如可以通过将消息唯一ID存储在数据中，为后续的事务状态回查提供唯一依据

        if (query(transferRecord.getRecordNo()) > 0) {
            return LocalTransactionState.COMMIT_MESSAGE;
        }
        return LocalTransactionState.UNKNOW;

    }

    /**
     * 事务消息状态回查
     * @param msg
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        TransferRecord transferRecord = JSON.parseObject(msg.getBody(), TransferRecord.class);

        if (query(transferRecord.getRecordNo()) > 0) {
            return LocalTransactionState.COMMIT_MESSAGE;
        }
        return LocalTransactionState.ROLLBACK_MESSAGE;
    }

    private int query(String recordNo) {
        int i = businessService.checkRecordNoExists(recordNo);
        return i;
    }


}
