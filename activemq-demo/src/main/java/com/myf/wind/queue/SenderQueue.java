package com.myf.wind.queue;

import com.myf.wind.config.MyConfig;
import com.myf.wind.pojo.Person;

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
        for (int i = 0; i < 10; i++) {
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("name","feifei-" + i);
            mapMessage.setStringProperty("name","feifei-" + i);
            mapMessage.setInt("age",25-i);
            mapMessage.setIntProperty("age",25-i);
            mapMessage.setString("gender","女");
            mapMessage.setStringProperty("gender","女");
            // 设置分组，定向分发
            mapMessage.setLongProperty("group",1);
            // 配置文件中需开启 schedulerSupport="true"
//            long delay = 6*1000;
//            int repeat = 2;
//            long period = 2*1000;
//            mapMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,delay);
//            // 重复需要设置为int类型
//            mapMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT,repeat);
//            mapMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD,period);
            // 超时后进入死信队列
            producer.setTimeToLive(10000);
            producer.send(mapMessage);
        }


        // 关闭连接
        connection.close();

    }


}
