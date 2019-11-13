package com.redhat.samples.amqp.support;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class TestSupport {

    private static final int BROKER_PORT = 5672;

    private static EmbeddedBroker broker;

    @BeforeClass
    public static void startBroker() throws Exception {
        broker = new EmbeddedBroker(BROKER_PORT).start();
    }

    @AfterClass
    public static void stopBroker() throws Exception {
        if (broker != null) {
            broker.stop();
            broker = null;
        }
    }

}
