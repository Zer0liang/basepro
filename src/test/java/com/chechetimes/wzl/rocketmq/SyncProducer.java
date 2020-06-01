package com.chechetimes.wzl.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * author:WangZhaoliang
 * Date:2020/5/16 10:59
 */
public class SyncProducer {

    private static final String SYNC_GROUP_NAME = "test_sync_group_name";
    private static final String NAME_SER_ADDRESS = "localhost:9876";

    /**
     * 同步发送消息生产者
     * 同步发送实时性快，可接受到是否消费结果
     * 重试机制：网络抖动导致消息发送失败，可设置重试几次
     *
     * producer.setRetryTimesWhenSendFailed(5);
     * producer.send(msg,5000L);
     *
     * @throws Exception
     */
    @Test
    public void testSyncProducer() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer(SYNC_GROUP_NAME);
        producer.setNamesrvAddr(NAME_SER_ADDRESS);
        producer.start();

        for (int i = 0; i < 1; i++) {
            Message msg = new Message("syncTopicTest", "syncTopicTagTestA",
                    ("hello rocketMq: " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            msg.setDelayTimeLevel(3);
            SendResult sendResult = producer.send(msg);
            System.out.println("msg:" + msg);
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }

    @Test
    public void testAsyncProducer() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer(SYNC_GROUP_NAME);
        producer.setNamesrvAddr(NAME_SER_ADDRESS);
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(5);

        int messageCount = 100;
        CountDownLatch2 countDownLatch2 = new CountDownLatch2(messageCount);
        for (int i = 0; i < messageCount; i++) {
            Message msg = new Message("TopicAsyncTest", "Tags", ("hello, asyncMsg" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            int index = i;
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(index + "send success! " + sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    e.printStackTrace();
                }
            });
        }
        countDownLatch2.await(5, TimeUnit.SECONDS);
        producer.shutdown();
    }

    /**
     * 顺序消费：生产消息的时候默认是采用轮询的方式将消息发送到不同的分区队列，消费消息的时候从多个队列中拉取消息，这样不能保证有序
     * 如果控制发送的消息都是一次发送到同一个queue中，消费的时候依次读取，则可以保证全局有序
     * 如果控制发送的消息都是根据某个特征发送到指定的queue中，则是分区有序，即保证每个分区内的消息都是有序的
     * 生产者：通过MessageQueueSelector设置发送到某个特征的queue中
     * 消费者：consumer.registerMessageListener(new MessageListenerOrderly() {
     */



    /**
     * 可对消息进行实时消费，并反馈消费结果
     * 拉取消息的模式：push pull? 两者有什么区别
     * 消费失败，重试机制？
     * 两种情况：
     *      生产者发送由于网络抖动发送消息失败，会立即重试，重试次数可在创建生产者时候指定
     *      消费者消费失败，是指在消费完成后消息返回：ConsumeConcurrentlyStatus.RECONSUME_LATER
     *      消费者可在1s 5s 10s ..... 2H范围内进行重试
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(SYNC_GROUP_NAME);
        consumer.setNamesrvAddr(NAME_SER_ADDRESS);
        consumer.subscribe("TopicAsyncTest", "*");

        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            System.out.println("threadName:" + Thread.currentThread().getName() + "\t msg:");
            for (MessageExt msg : msgs) {
                System.out.println(new String(msg.getBody()));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        System.out.println("consumer started!");
    }

}
