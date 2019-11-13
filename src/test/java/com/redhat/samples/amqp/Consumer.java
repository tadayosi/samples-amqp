package com.redhat.samples.amqp;

import javax.jms.JMSException;

public interface Consumer extends AutoCloseable {

    void receive(long timeout) throws JMSException;

}
