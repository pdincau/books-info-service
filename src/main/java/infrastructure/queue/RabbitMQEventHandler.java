package infrastructure.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import domain.EventHandler;
import domain.ViewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RabbitMQEventHandler implements EventHandler {

    static final Logger LOG = LoggerFactory.getLogger(RabbitMQEventHandler.class);

    private final static String EXCHANGE_NAME = "book_events";
    public static final String BOOK_CREATED_TOPIC = "BookCreated";
    private ViewRepository viewRepository;

    public RabbitMQEventHandler(ViewRepository viewRepository) {
        this.viewRepository = viewRepository;
    }

    @Override
    public void listen() {
        LOG.info("Waiting for events from queue.");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, BOOK_CREATED_TOPIC);
            Consumer consumer = new BookEventsConsumer(channel, viewRepository);
            channel.basicConsume(queueName, true, consumer);
        } catch (IOException e) {
            LOG.error("Failed while listening from exchange: {}");
        }
    }
}
