package com.jmsapp.interfaces;


public interface EmbeddedMessageSaverMBean {
    public void setBrokerName(String brokerName);
    public String getBrokerName();

    public void setQueueName(String queueName);
    public String getQueueName();
}
