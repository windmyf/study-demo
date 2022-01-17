package com.myf.wind.temporary;

import com.myf.wind.config.MyConfig;

import javax.jms.*;

/**
 * @author : wind-myf
 * @desc : 消息接收
 * @version : 1.0
 */
public class TemporaryReceiverQueue {
    public static void main(String[] args) throws JMSException {

        // 获取连接
        Connection connection = MyConfig.getActiveMQConnection();
        // 获取session false:不使用事务
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue destination = session.createQueue("temporary");
        MessageConsumer consumer = session.createConsumer(destination);

        // 获取消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println("接收到一条消息");
                System.out.println("开始发送确认消息-----------");
                try {
                    Destination jmsReplyTo = message.getJMSReplyTo();
                    MessageProducer producer = session.createProducer(jmsReplyTo);
                    producer.send(session.createTextMessage("I received your message"));
                } catch (JMSException e) {
                    e.printStackTrace();
                }

            }
        });

    }








}
