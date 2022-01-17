package com.myf.wind.config;

import com.myf.wind.pojo.Person;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import java.util.ArrayList;

/**
 * @author : wind-myf
 * @desc : 获取配置信息
 * @version : 1.0
 */
public class MyConfig {
    public static Connection getActiveMQConnection() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "admin", "windmyf123",
                "tcp://47.96.167.160:61616"
        );
        // 添加信任的持久化类型
        ArrayList<String> trustedPackages = new ArrayList<>();
        trustedPackages.add(Person.class.getPackage().getName());
        trustedPackages.add(String.class.getPackage().getName());
        trustedPackages.add(Integer.class.getPackage().getName());
        connectionFactory.setTrustedPackages(trustedPackages);
        // 获取连接
        return connectionFactory.createConnection();
    }
}
