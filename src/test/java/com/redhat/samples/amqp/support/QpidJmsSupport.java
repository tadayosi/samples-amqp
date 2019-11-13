package com.redhat.samples.amqp.support;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.qpid.jms.JmsConnectionFactory;

public class QpidJmsSupport implements AutoCloseable {

    private Connection connection;
    protected Session session;
    protected Destination queue;

    public QpidJmsSupport(String url, String queueName) throws JMSException {
        ConnectionFactory factory = new JmsConnectionFactory(url);
        connection = factory.createConnection();
        connection.start();

        session = connection.createSession(
            false, Session.AUTO_ACKNOWLEDGE);
        queue = session.createQueue(queueName);
    }

    @Override
    public void close() {
        try {
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.stop();
                connection.close();
            }
        } catch (JMSException e) {
        } finally {
            connection = null;
            session = null;
            queue = null;
        }
    }
}
