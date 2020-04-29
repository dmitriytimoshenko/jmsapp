package com.jmsapp;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

import javax.jms.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmbeddedMessageSender {

    // Адрес брокера
    private static String brokerName = "henry";

    // Имя очереди
    private static String queueName = "SEND_QUEUE";


    public void sendFiveMessages() throws JMSException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        // Создаем Embedded брокер
        BrokerService brokerService = new BrokerService();
        brokerService.setBrokerName(brokerName);

        try {

            brokerService.addConnector("vm://henry?brokerConfig=xbean:activemq.xml");

            brokerService.start();

            ConnectionFactory confactory = new ActiveMQConnectionFactory("vm://" + brokerName);

            // Создаем соединение
            Connection connection = confactory.createConnection();
            connection.start();

            // Создаем сессию
            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);

            // Создаем очередь
            Destination destination = session.createQueue(queueName);

            // Создаем отправителя
            MessageProducer producer = session.createProducer(destination);

            // сообщение для отправки

            TextMessage messageOne = session.createTextMessage("Hello, Budas!");
            messageOne.setJMSDeliveryMode(DeliveryMode.PERSISTENT);

            System.out.println("Start sending: " + dateFormat.format(new Date()));

            for (int i = 0; i < 1000; i++) {
                for (int j = 0; j < 100 ; j++) {
                    producer.send(messageOne);
                    //System.out.println("SEND_QUEUE printing: " + messageOne.getText());
                    session.commit();
                }
            }

            connection.close();
            System.out.println("End sending: " + dateFormat.format(new Date()));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
