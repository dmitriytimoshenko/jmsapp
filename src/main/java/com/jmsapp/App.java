package com.jmsapp;


import javax.jms.JMSException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        EmbeddedMessageSender messageSender = new EmbeddedMessageSender();
        EmbeddedMessageSaver messageSaver = new EmbeddedMessageSaver();

        try {
            messageSender.sendFiveMessages();
        } catch (JMSException e) {
            System.out.println("Ошибка отправки сообщения: " + e.getErrorCode());
        }


        try {
            messageSaver.getFiveMessages();
        } catch (JMSException e) {
            System.out.println("Ошибка получения сообщения: " + e.getErrorCode());
        }


        /*
        MessageSender messageSender = new MessageSender();
        MessageSaver messageSaver = new MessageSaver();

           try {
                messageSender.sendFiveMessages();
            } catch (JMSException e) {
                System.out.println("Ошибка отправки сообщения: " + e.getErrorCode());
            }


            try {
                messageSaver.getFiveMessages();
            } catch (JMSException e) {
                System.out.println("Ошибка получения сообщения: " + e.getErrorCode());
            }

         */

    }
}
