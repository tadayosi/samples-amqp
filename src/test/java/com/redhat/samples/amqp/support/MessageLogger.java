package com.redhat.samples.amqp.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;

import com.google.common.base.Strings;
import org.apache.qpid.jms.message.JmsMessage;
import org.apache.qpid.jms.provider.amqp.message.AmqpJmsMessageFacade;
import org.apache.qpid.jms.provider.amqp.message.AmqpJmsMessagePropertyIntercepter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageLogger.class);

    private static void log(String label, Message message) {
        LOGGER.info(Strings.repeat("=", 50));
        try {
            LOGGER.info("{}: '{}'", label, message.getBody(String.class));
            logProperties(message);
            logAmqpAnnotations(message);
        } catch (JMSException e) {
            LOGGER.error(e.getMessage(), e);
        }
        LOGGER.info(Strings.repeat("=", 50));
    }

    private static void logProperties(Message message) throws JMSException {
        List<String> names = Collections.list(message.getPropertyNames());
        Collections.sort(names);
        for (String name : names) {
            LOGGER.info("[prop] {} = {}", name, message.getStringProperty((String) name));
        }
    }

    private static void logAmqpAnnotations(Message message) throws JMSException {
        if (message instanceof JmsMessage && ((JmsMessage) message).getFacade() instanceof AmqpJmsMessageFacade) {
            AmqpJmsMessageFacade facade = (AmqpJmsMessageFacade) ((JmsMessage) message).getFacade();

            LOGGER.info("[prop names] {}", AmqpJmsMessagePropertyIntercepter.getAllPropertyNames());

            // application properties
            List<String> names = new ArrayList<>(facade.getApplicationPropertyNames(new HashSet<>()));
            Collections.sort(names);
            for (String name : names) {
                LOGGER.info("[app-prop] {} = {}", name, facade.getApplicationProperty(name));
            }

            // message annotations
            facade.filterTracingAnnotations((key, value) -> {
                LOGGER.info("[msg-annotation] {} = {}", key, value);
            });

            // delivery annotations
        }
    }

    public static void send(Message message) {
        log("Send", message);
    }

    public static void receive(Message message) {
        log("Receive", message);
    }

}
