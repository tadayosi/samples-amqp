package com.redhat.samples.amqp;

import java.util.Map;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

import com.redhat.samples.amqp.support.MessageLogger;
import com.redhat.samples.amqp.support.QpidJmsSupport;

public class QpidJmsProducer extends QpidJmsSupport implements Producer {

    public QpidJmsProducer(String url, String queueName) throws JMSException {
        super(url, queueName);
    }

    @Override
    public void send(String request, Map<String, String> properties) throws JMSException {
        TextMessage message = session.createTextMessage(request);
        try (MessageProducer producer = session.createProducer(queue)) {
            for (String key : properties.keySet()) {
                message.setStringProperty(key, properties.get(key));
            }
            MessageLogger.send(message);
            producer.send(message);
        }
    }

}
