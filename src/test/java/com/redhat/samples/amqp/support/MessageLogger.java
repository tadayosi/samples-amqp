package com.redhat.samples.amqp.support;

import java.util.Collections;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageLogger.class);

    private static void log(String label, Message message) {
        LOGGER.info(Strings.repeat("=", 50));
        try {
            LOGGER.info("{}: '{}'", label, message.getBody(String.class));
            List<String> names = Collections.list(message.getPropertyNames());
            Collections.sort(names);
            for (String name : names) {
                LOGGER.info("[prop] {} = {}", name, message.getStringProperty((String) name));
            }
        } catch (JMSException e) {
            LOGGER.error(e.getMessage(), e);
        }
        LOGGER.info(Strings.repeat("=", 50));
    }

    public static void send(Message message) {
        log("Send", message);
    }

    public static void receive(Message message) {
        log("Receive", message);
    }

}
