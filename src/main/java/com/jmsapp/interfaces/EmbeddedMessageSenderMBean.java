package com.jmsapp.interfaces;

public interface EmbeddedMessageSenderMBean {

    public String getBrokerName();
    public String getQueueName();


    public void setAttributes(String brokerName, String queueName);
    public void sendFiveMessages();

}
