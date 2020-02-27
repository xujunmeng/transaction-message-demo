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
import com.alibaba.fastjson.parser.Feature;
import com.demo.biz.entity.TransferRecord;
import com.demo.constant.TransactionMessageCons;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * This example shows how to subscribe and consume messages using providing {@link DefaultMQPushConsumer}.
 */
@Component
public class Consumer {

    @Autowired
    private BusinessService businessService;

    @PostConstruct
    public void defaultMQPushConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(TransactionMessageCons.consumerGroup);
        consumer.setNamesrvAddr(TransactionMessageCons.namesrvAddr);
        try {
            consumer.subscribe(TransactionMessageCons.topic, TransactionMessageCons.tag);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
                for (MessageExt msg : msgs) {
                    String msgStr = JSON.toJSONString(msg.getBody());
                    System.out.println("收到消息 : " + msgStr);

                    //执行加钱操作
                    TransferRecord transferRecord = JSON.parseObject(msgStr, TransferRecord.class, new Feature[]{Feature.AllowISO8601DateFormat});
                    businessService.handleAddMoney(transferRecord);

                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });

            consumer.start();
            System.out.printf("Consumer Started.%n");

        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
}
