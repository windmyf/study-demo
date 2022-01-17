package com.myf.wind.jmsid;

import com.myf.wind.config.MyConfig;

import javax.jms.*;

/**
 * @author : wind-myf
 * @desc : 消息接收
 * @version : 1.0
 */
public class JmsIdReceiverQueue {
    public static void main(String[] args) throws JMSException {

        // 获取连接
        Connection connection = MyConfig.getActiveMQConnection();
        // 获取session false:不使用事务
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue jmsid = session.createQueue("jmsid");
        // 此处设置过滤器，防止自己消费自己
        MessageConsumer consumer = session.createConsumer(jmsid,"type='C'");

        // 获取消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println("接收到一条消息");
                System.out.println("开始发送确认消息-----------");
                try {
                    MessageProducer producer = session.createProducer(jmsid);
                    TextMessage textMessage = session.createTextMessage("I received your message");
                    textMessage.setJMSCorrelationID(message.getJMSCorrelationID());
                    textMessage.setStringProperty("type","P");
                    producer.send(textMessage);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
                System.out.println("确认消息发送完成-------");
            }
        });

    }








}
