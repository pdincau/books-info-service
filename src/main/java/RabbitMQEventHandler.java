import com.rabbitmq.client.*;
import domain.EventHandler;
import infrastructure.BookEventsConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RabbitMQEventHandler implements EventHandler {

    static final Logger LOG = LoggerFactory.getLogger(RabbitMQEventHandler.class);

    private final static String QUEUE_NAME = "book_events";

    @Override
    public void listen() {
        LOG.info("Waiting for events from queue.");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            Consumer consumer = new BookEventsConsumer(channel);
            channel.basicConsume(QUEUE_NAME, true, consumer);
        } catch (IOException e) {
            LOG.error("Failed while listening from queue: {}", QUEUE_NAME);
        }
    }
}
