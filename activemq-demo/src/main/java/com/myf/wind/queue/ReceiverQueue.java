package com.myf.wind.queue;

import com.myf.wind.config.MyConfig;
import com.myf.wind.config.MyListener;

import javax.jms.*;

/**
 * @author : wind-myf
 * @desc : 消息接收
 * @version : 1.0
 */
public class ReceiverQueue {
    public static void main(String[] args) throws JMSException {

        // 获取连接
        Connection connection = MyConfig.getActiveMQConnection();
        connection.start();
        // 获取session false:不使用事务
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 死信队列
        Queue dlq = session.createQueue("ActiveMQ.DLQ");
        MessageConsumer dlqConsumer = session.createConsumer(dlq);
        dlqConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                MapMessage mapMessage = (MapMessage) message;
                System.out.println("mapMessage-DLQ = " + mapMessage);
                try {
                    System.out.println("dlq-------------------");
                    System.out.println("name-DLQ = " + mapMessage.getString("name"));
                    System.out.println("age-DLQ = " + mapMessage.getString("age"));
                    System.out.println("gender-DLQ = " + mapMessage.getString("gender"));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        // 获取destination,user?consumer.exclusive=true设置一个消费者消费（独占式消费），避免数据分布到不同的节点
        Queue destination = session.createQueue("user");
        // 可多条件
        String selector = "group=1";
        // 向目的地写入消息,分组消费(并发场景下提高并发量，负载均衡概念)
        MessageConsumer consumer = session.createConsumer(destination,selector);
        // 获取消息
        consumer.setMessageListener(new MyListener());

    }








}
