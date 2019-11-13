jms-send:
	mvn test -q -Dtest=AmqpTest#jmsSend

jms-receive:
	mvn test -q -Dtest=AmqpTest#jmsReceive
