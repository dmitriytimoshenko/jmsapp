package com.jmsapp;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

import javax.jms.*;

public class EmbeddedMessageSender {

    // Адрес брокера
    private static String brokerName = "henry";

    // Имя очереди
    private static String queueName = "SEND_QUEUE";


    public void sendFiveMessages() throws JMSException {

        // Создаем Embedded брокер
        BrokerService brokerService = new BrokerService();
        brokerService.setBrokerName(brokerName);

        try {

            brokerService.addConnector("vm://" + brokerName);

            brokerService.start();

            ConnectionFactory confactory = new ActiveMQConnectionFactory("vm://" + brokerName);

            // Создаем соединение
            Connection connection = confactory.createConnection();
            connection.start();

            // Создаем сессию
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Создаем очередь
            Destination destination = session.createQueue(queueName);

            // Создаем отправителя
            MessageProducer producer = session.createProducer(destination);

            // сообщение для отправки

            TextMessage messageOne = session.createTextMessage("Hello, Budas!");
            messageOne.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
            for (int i = 0; i < 20000; i++) {

                producer.send(messageOne);
                System.out.println("SEND_QUEUE printing: " + messageOne.getText());

            }
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
