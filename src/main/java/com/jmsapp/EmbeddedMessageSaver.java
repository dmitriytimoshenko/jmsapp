package com.jmsapp;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import javax.jms.*;

public class EmbeddedMessageSaver {

    // Адрес брокера
    private static String brokerName = "henry";

    // Имя очереди
    private static String queueName = "SEND_QUEUE";


    public void getFiveMessages() throws JMSException {

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
            MessageConsumer consumer = session.createConsumer(destination);

            // Принимаем сообщения

            for (int i = 0; i < 20000; i++) {
                Message message = consumer.receive();

                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println("Recieved message: '" + textMessage.getText() + "'");
                }
            }
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
