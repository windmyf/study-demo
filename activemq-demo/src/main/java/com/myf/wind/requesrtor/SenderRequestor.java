package com.myf.wind.requesrtor;

import com.myf.wind.config.MyConfig;
import org.apache.activemq.ActiveMQConnection;

import javax.jms.*;

/**
 * @author : wind-myf
 * @desc : 同步消息（极强一致性）
 * @version : 1.0
 */
public class SenderRequestor {
    public static void main(String[] args) throws JMSException {

        // 获取连接
        ActiveMQConnection connection = (ActiveMQConnection)MyConfig.getActiveMQConnection();
        // 获取session true:使用事务
        QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();
        System.out.println("--------------- SenderRequestor-started ---------------");
        // 获取destination
        Queue queue = session.createQueue("requestor");
        MessageProducer producer = session.createProducer(queue);
        QueueRequestor queueRequestor = new QueueRequestor(session, queue);
        System.out.println("==== 等待发送请求 ====");
        TextMessage textMessage = (TextMessage) queueRequestor.request(session.createMessage());
        System.out.println("==== 请求发完了 ====");

        System.out.println("responseMsg : " + textMessage.getText());
        // 关闭连接
        connection.close();

    }
    

}
