package com.myf.wind.topic;

import com.myf.wind.config.MyConfig;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author : wind-myf
 * @desc : 消息接收
 * @version : 1.0
 */
public class ReceiverTopic {
    public static void main(String[] args) throws JMSException {

        // 获取连接
        Connection connection = MyConfig.getActiveMQConnection();
        connection.start();
        // 获取session false:不使用事务
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 获取destination
        Destination destination = session.createTopic("userTopic");
        // 向目的地写入消息
        MessageConsumer consumer = session.createConsumer(destination);
        for (int i = 0; ; i++) {

            TextMessage receive = (TextMessage)consumer.receive();
            System.out.println("receive ------------- " + receive.getText());
        }
        // 关闭连接
//        connection.close();

    }








}
