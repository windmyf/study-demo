package com.myf.wind.jmsid;

import com.myf.wind.config.MyConfig;

import javax.jms.*;
import java.util.UUID;

/**
 * @author : wind-myf
 * @desc : 消息发送
 * @version : 1.0
 */
public class JmsIdSenderQueue {
    public static void main(String[] args) throws JMSException {

        // 获取连接
        Connection connection = MyConfig.getActiveMQConnection();
        // 获取session true:使用事务
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();
        System.out.println("-------------JmsIdSenderQueue started-------------");

        Queue jmsid = session.createQueue("jmsid");
        MessageProducer producer = session.createProducer(jmsid);
        TextMessage textMessage = session.createTextMessage("JmsIdSenderQueue - message to be send");
        String cid = UUID.randomUUID().toString();
        textMessage.setJMSCorrelationID(cid);
        textMessage.setStringProperty("type","C");
        producer.send(textMessage);
        System.out.println("----- 消息发送完成-----");

        // 等待消息确认的消费者
        MessageConsumer consumer = session.createConsumer(jmsid,"JMSCorrelationID='" + cid + "' and type='P'");
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    System.out.println("收到消息确认:" + ((TextMessage)message).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        // 关闭连接
//        connection.close();

    }


}
