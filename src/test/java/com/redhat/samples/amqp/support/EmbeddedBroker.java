package com.redhat.samples.amqp.support;

import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.ActiveMQServers;

public class EmbeddedBroker {

    private int port;
    private final ActiveMQServer server;

    public EmbeddedBroker(int port) throws Exception {
        this.port = port;
        server = createServer();
    }

    private ActiveMQServer createServer() throws Exception {
        ActiveMQServer server = ActiveMQServers.newActiveMQServer(
            new ConfigurationImpl()
                .setPersistenceEnabled(false)
                .setJournalDirectory("target/data/journal")
                .setSecurityEnabled(false)
                .addAcceptorConfiguration("amqp", "tcp://0.0.0.0:" + port));
        return server;
    }

    public EmbeddedBroker start() throws Exception {
        server.start();
        return this;
    }

    public EmbeddedBroker stop() throws Exception {
        server.stop();
        return this;
    }
}
