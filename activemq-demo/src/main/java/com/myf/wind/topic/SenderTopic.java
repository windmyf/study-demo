package com.myf.wind.topic;

import com.myf.wind.config.MyConfig;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author : wind-myf
 * @desc : 消息发送
 * @version : 1.0
 */
public class SenderTopic {
    public static void main(String[] args) throws JMSException {

        // 获取连接
        Connection connection = MyConfig.getActiveMQConnection();
        // 获取session true:使用事务
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 获取destination
        Destination destination = session.createTopic("userTopic");
        // 向目的地写入消息
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        for (int i = 0; i < 100; i++) {
            TextMessage textMessage = session.createTextMessage("hello-" + i);
            producer.send(textMessage);
            System.out.println("textMessage = " + textMessage.getText());
        }
        // 关闭连接
        connection.close();

    }








}
