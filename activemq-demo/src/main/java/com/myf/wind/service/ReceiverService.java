package com.myf.wind.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Service
public class ReceiverService {

    @JmsListener(destination = "springboot",containerFactory = "jmsListenerContainerTopic")
    public void receive(String msg){
        System.out.println("receive msg ================== " + msg);
    }

    @JmsListener(destination = "springboot",containerFactory = "jmsListenerContainerTopic")
    public void receiveJms(TextMessage msg){
        try {
            System.out.println("receive msg ---------------- " + msg.getText());
            System.out.println("receive msg = " + msg.getStringProperty("name"));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
