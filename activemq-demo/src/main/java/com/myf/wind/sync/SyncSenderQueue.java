package com.myf.wind.sync;

import com.myf.wind.config.MyConfig;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import javax.jms.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author : wind-myf
 * @desc : 消息发送
 * @version : 1.0
 */
public class SyncSenderQueue {
    public static void main(String[] args) throws JMSException, InterruptedException {

        // 获取连接
        Connection connection = MyConfig.getActiveMQConnection();
        // 获取session true:使用事务
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();
        System.out.println("====== SyncSenderQueue started======");
        Queue sync = session.createQueue("sync");
        ActiveMQMessageProducer producer = (ActiveMQMessageProducer)session.createProducer(sync);
        TextMessage textMessage = session.createTextMessage("sync-message - A");
        final CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            // 回调
            producer.send(textMessage, new AsyncCallback() {
                @Override
                public void onSuccess() {
                    countDownLatch.countDown();
                }

                @Override
                public void onException(JMSException e) {
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.await();

        System.out.println("system exit.......");

        // 关闭连接
        connection.close();

    }


}
