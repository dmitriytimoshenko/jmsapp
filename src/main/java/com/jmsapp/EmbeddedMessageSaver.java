package com.jmsapp;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import javax.jms.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmbeddedMessageSaver {

    // Адрес брокера
    private static String brokerName = "henry";

    // Имя очереди
    private static String queueName = "SEND_QUEUE";


    public void getFiveMessages() throws JMSException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
/*
        // Создаем Embedded брокер
        BrokerService brokerService = new BrokerService();
        brokerService.setBrokerName(brokerName);

        try {
            brokerService.addConnector("tcp://localhost:61616");

            brokerService.start();
*/
            ConnectionFactory confactory = new ActiveMQConnectionFactory("vm://" + brokerName);

            // Создаем соединение
            Connection connection = confactory.createConnection();
            connection.start();

            // Создаем сессию
            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);

            // Создаем очередь
            Destination destination = session.createQueue(queueName);

            // Создаем отправителя
            MessageConsumer consumer = session.createConsumer(destination);

            // Принимаем сообщения
            System.out.println("Start recieve: " + dateFormat.format(new Date()));

            for (int i = 0; i < 1000; i++) {
                for (int j = 0; j < 100; j++) {
                    Message message = consumer.receive();
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        //System.out.println("Recieved message: '" + textMessage.getText() + "'");
                        session.commit();
                    }
                }

            }

            System.out.println("End recieve: " + dateFormat.format(new Date()));
            connection.close();
/*
        } catch (Exception e) {
            e.printStackTrace();
        }

 */
    }
}
