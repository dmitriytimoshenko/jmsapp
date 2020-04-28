package com.jmsapp;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MessageSaver {

    // Адрес брокера
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // Имя очереди
    private static String queueName = "SEND_QUEUE";



    public void getFiveMessages() throws JMSException {

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
        MessageConsumer consumer = session.createConsumer(destination);

        // Принимаем сообщения

        for (int i = 0; i < 50000; i++) {
            for (int j = 0; j < 20; j++) {
                Message message = consumer.receive();

                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println("Recieved message: '" + textMessage.getText() + "'");
                }

            }
            session.commit();
        }
        connection.close();
    }
}
