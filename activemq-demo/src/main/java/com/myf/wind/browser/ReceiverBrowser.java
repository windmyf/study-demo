package com.myf.wind.browser;

import com.myf.wind.config.MyConfig;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;
import java.util.Enumeration;

/**
 * @author : wind-myf
 * @desc : 消息接收
 * @version : 1.0
 */
public class ReceiverBrowser {
    public static void main(String[] args) throws JMSException {

        // 获取连接
        Connection connection = MyConfig.getActiveMQConnection();
        connection.start();
        System.out.println("----------ReceiverBrowser-started----------");
        // 获取session false:不使用事务
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 获取destination,user?consumer.exclusive=true设置一个消费者消费（独占式消费），避免数据分布到不同的节点
        MessageConsumer consumer = session.createConsumer(new ActiveMQQueue("browser"));
        QueueBrowser browser = session.createBrowser(new ActiveMQQueue("browser"));
        Enumeration enumeration = browser.getEnumeration();
        while (enumeration.hasMoreElements()){
            TextMessage textMessage = (TextMessage) enumeration.nextElement();
            System.out.println("textMessage = " + textMessage.getText());
            System.out.println(textMessage.getJMSCorrelationID());
        }

    }








}
