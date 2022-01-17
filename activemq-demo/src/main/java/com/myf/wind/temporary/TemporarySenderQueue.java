package com.myf.wind.temporary;

import com.myf.wind.config.MyConfig;

import javax.jms.*;

/**
 * @author : wind-myf
 * @desc : 消息发送
 * @version : 1.0
 */
public class TemporarySenderQueue {
    public static void main(String[] args) throws JMSException {

        // 获取连接
        Connection connection = MyConfig.getActiveMQConnection();
        // 获取session true:使用事务
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();
        System.out.println("-------------TemporarySenderQueue started-------------");

        Queue temporary = session.createQueue("temporary");
        MessageProducer producer = session.createProducer(temporary);
        TextMessage textMessage = session.createTextMessage("TemporarySenderQueue - message to be send");
        TemporaryQueue temporaryQueue = session.createTemporaryQueue();
        textMessage.setJMSReplyTo(temporaryQueue);
        producer.send(textMessage);

        // 等待消息确认的消费者
        MessageConsumer consumer = session.createConsumer(temporaryQueue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println("收到消息确认:" + message);
            }
        });

        // 关闭连接
//        connection.close();

    }


}
