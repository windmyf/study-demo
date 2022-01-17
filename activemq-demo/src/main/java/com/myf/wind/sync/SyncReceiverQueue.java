package com.myf.wind.sync;

import com.myf.wind.config.MyConfig;
import com.myf.wind.config.MyListener;

import javax.jms.*;

/**
 * @author : wind-myf
 * @desc : 消息接收
 * @version : 1.0
 */
public class SyncReceiverQueue {
    public static void main(String[] args) throws JMSException {

        // 获取连接
        Connection connection = MyConfig.getActiveMQConnection();
        // 获取session false:不使用事务
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();
        System.out.println("--------------- SyncReceiverQueue-started ---------------");
        // 获取destination
        Queue queue = session.createQueue("sync");
        MessageConsumer consumer = session.createConsumer(queue);
        // 获取消息
        consumer.setMessageListener(new MyListener());

    }

}
