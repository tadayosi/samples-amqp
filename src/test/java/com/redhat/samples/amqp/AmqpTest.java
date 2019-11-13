package com.redhat.samples.amqp;

import javax.jms.Message;
import javax.jms.TextMessage;

import com.google.common.collect.ImmutableMap;
import com.redhat.samples.amqp.support.MessageLogger;
import org.junit.Test;

public class AmqpTest /* extends TestSupport */ {

    private static final String BROKER_URL = "amqp://localhost:5672";
    private static final String QUEUE = "sample.queue";

    @Test
    public void jmsSend() throws Exception {
        try (Producer producer = new QpidJmsProducer(BROKER_URL, QUEUE)) {
            producer.send("Hello from JMS!", ImmutableMap.of(
                "aaa", "xxx",
                "bbb", "yyy",
                "ccc", "zzz"
            ));
        }
    }

    @Test
    public void jmsReceive() throws Exception {
        try (Consumer consumer = new QpidJmsConsumer(BROKER_URL, QUEUE, MessageLogger::receive)) {
            consumer.receive(3000);
        }
    }

}
