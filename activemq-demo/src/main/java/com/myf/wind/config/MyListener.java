package com.myf.wind.config;

import com.myf.wind.pojo.Person;

import javax.jms.*;
import java.io.Serializable;

/**
 * @author : wind-myf
 * @desc : 消息监听器
 * @version : 1.0
 */
public class MyListener implements MessageListener {
    @Override
    public void onMessage(Message message) {

        if (message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println(textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }else if (message instanceof ObjectMessage){
            ObjectMessage objectMessage = (ObjectMessage) message;
            Person person = null;
            try {
                person = (Person)objectMessage.getObject();
            } catch (JMSException e) {
                e.printStackTrace();
            }
            System.out.println(person);
        }else if (message instanceof BytesMessage){
            BytesMessage bytesMessage = (BytesMessage) message;
            try {
                // String utf = bytesMessage.readUTF();
                byte[] bytes = new byte[1024];
                int len = -1;
                while ((len = bytesMessage.readBytes(bytes)) != -1){
                    System.out.println(new String(bytes,0,len));
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }else if (message instanceof MapMessage){
            MapMessage mapMessage = (MapMessage) message;
            System.out.println("mapMessage = " + mapMessage);
            try {
                System.out.println("name = " + mapMessage.getString("name"));
                System.out.println("age = " + mapMessage.getString("age"));
                System.out.println("gender = " + mapMessage.getString("gender"));
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }
}
