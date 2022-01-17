package com.myf.wind.browser;

import com.myf.wind.config.MyConfig;

import javax.jms.*;

/**
 * @author : wind-myf
 * @desc : 消息发送
 * @version : 1.0
 */
public class SenderBrowser {
    public static void main(String[] args) throws JMSException {

        // 获取连接
        Connection connection = MyConfig.getActiveMQConnection();
        // 获取session true:使用事务
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 获取destination
        Queue queue = session.createQueue("browser");
        MessageProducer producer = session.createProducer(queue);
        // 消息发送
        TextMessage textMessage = session.createTextMessage("senderBrowser message-A");
        textMessage.setJMSCorrelationID("test01");
        producer.send(textMessage);
        // 关闭连接
        connection.close();

    }
    

}
