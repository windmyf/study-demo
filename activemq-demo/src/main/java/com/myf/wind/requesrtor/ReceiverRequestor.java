package com.myf.wind.requesrtor;

import com.myf.wind.config.MyConfig;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

/**
 * @author : wind-myf
 * @desc : 消息接收（保证一致性）
 * @version : 1.0
 */
public class ReceiverRequestor {
    public static void main(String[] args) throws JMSException {

        // 获取连接
        Connection connection = MyConfig.getActiveMQConnection();
        connection.start();
        System.out.println("----------ReceiverRequestor-started----------");
        // 获取session false:不使用事务
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer(new ActiveMQQueue("requestor"));
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println("收到一条消息。。。。。");
                System.out.println("开始发送确认消息");
                try {
                    Destination replyTo = message.getJMSReplyTo();
                    MessageProducer producer = session.createProducer(replyTo);
                    producer.send(session.createTextMessage());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
                System.out.println("确认消息发送完成。。。。。。");

            }
        });

    }

}
