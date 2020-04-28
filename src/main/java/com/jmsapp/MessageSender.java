package com.jmsapp;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MessageSender {

    // Адрес брокера
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // Имя очереди
    private static String queueName = "SEND_QUEUE";


    public void sendFiveMessages() throws JMSException {

        // Получаем коннект брокера
        ConnectionFactory confactory = new ActiveMQConnectionFactory(url);

        // Создаем соединение
        Connection connection = confactory.createConnection();
        connection.start();

        // Создаем сессию
        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);

        // Создаем получателя
        Destination destination = session.createQueue(queueName);

        // Создаем отправителя
        MessageProducer producer = session.createProducer(destination);

        // сообщение для отправки

        TextMessage messageOne = session.createTextMessage("Hello, Budas!");
        messageOne.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
        for (int i = 0; i < 50000; i++) {
            for (int j = 0; j < 20; i++) {
                producer.send(messageOne);
                System.out.println("SEND_QUEUE printing: " + messageOne.getText());

            }
            session.commit();

        }
        connection.close();
    }
}
