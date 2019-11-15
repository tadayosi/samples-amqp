package com.redhat.samples.amqp;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;

import com.redhat.samples.amqp.support.QpidJmsSupport;

public class QpidJmsConsumer extends QpidJmsSupport implements Consumer {

    private MessageListener listener;

    public QpidJmsConsumer(String url, String queueName, MessageListener listener) throws JMSException {
        super(url, queueName);
        this.listener = listener;
    }

    @Override
    public void receive(long timeout) throws JMSException {
        try (MessageConsumer consumer = session.createConsumer(queue)) {
            Message message = consumer.receive(timeout);
            if (message != null) {
                listener.onMessage(message);
            }
        }
    }

}
