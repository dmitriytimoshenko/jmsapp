package com.jmsapp;


import com.jmsapp.interfaces.EmbeddedMessageSaverMBean;
import com.jmsapp.interfaces.EmbeddedMessageSenderMBean;

import javax.jms.JMSException;
import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws MalformedObjectNameException, InstanceAlreadyExistsException, NotCompliantMBeanException, MBeanRegistrationException, InterruptedException {
        // Управление через JMX
        MBeanServer mbserver = ManagementFactory.getPlatformMBeanServer();
        ObjectName sender = new ObjectName("com.jmsapp:type=EmbeddedMessageSender");
        ObjectName saver = new ObjectName("com.jmsapp:type=EmbeddedMessageSaver");
        EmbeddedMessageSender messageSender = new EmbeddedMessageSender();
        EmbeddedMessageSaver messageSaver = new EmbeddedMessageSaver();

        StandardMBean msender = new StandardMBean(messageSender, EmbeddedMessageSenderMBean.class);
        StandardMBean msaver = new StandardMBean(messageSaver, EmbeddedMessageSaverMBean.class);
        mbserver.registerMBean(msender, sender);
        mbserver.registerMBean(msaver, saver);
        //----------------

            messageSender.sendFiveMessages();



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
