package com.myf.wind.queue;

import com.myf.wind.config.MyConfig;
import com.myf.wind.pojo.Person;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author : wind-myf
 * @desc : 消息发送
 * @version : 1.0
 */
public class SenderQueue {
    public static void main(String[] args) throws JMSException {

        // 获取连接
        Connection connection = MyConfig.getActiveMQConnection();
        // 获取session true:使用事务
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 获取destination
        Queue queue = session.createQueue("user");
        // 向目的地写入消息
        MessageProducer producer = session.createProducer(queue);
        // 是否持久化（默认不持久化的消息不会进入死信队列，可以通过配置文件设置进入）
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        Person person = new Person("Jone", 25, "男");
        ObjectMessage objectMessage = session.createObjectMessage(person);
        producer.send(objectMessage);

        // 传文件用(内存限制？)
        BytesMessage bytesMessage = session.createBytesMessage();
        bytesMessage.writeUTF("Hi-Hi~~~");
        producer.send(bytesMessage);

        // map类型
        for (int i = 0; i < 100; i++) {
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("name","feifei-" + i);
            mapMessage.setInt("age",25);
            mapMessage.setString("gender","女");
            // 设置分组，定向分发
            mapMessage.setLongProperty("group",i%7);
            // 超时后进入死信队列
            producer.setTimeToLive(1000);
            producer.send(mapMessage);
        }


        // 关闭连接
        connection.close();

    }








}
