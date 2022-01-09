package com.myf.wind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Service
public class SendService {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(String destination,String msg){

        // 传入的对象自动转化
        jmsMessagingTemplate.convertAndSend(destination,msg);
    }

    public void sendJms(String destination, final String msg){
        // 使用jmsTemplate可自定义参数
        jmsTemplate.send(destination,new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage testMessage = session.createTextMessage(msg);
                testMessage.setStringProperty("name","hello~~~");

                return testMessage;
            }
        });
//        jmsMessagingTemplate.convertAndSend(destination,msg);
    }
}
