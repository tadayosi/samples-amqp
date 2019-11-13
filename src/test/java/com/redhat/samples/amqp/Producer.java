package com.redhat.samples.amqp;

import java.util.Collections;
import java.util.Map;
import javax.jms.JMSException;

public interface Producer extends AutoCloseable {

    default void send(String message) throws JMSException {
        send(message, Collections.emptyMap());
    }

    void send(String request, Map<String, String> properties) throws JMSException;

}
