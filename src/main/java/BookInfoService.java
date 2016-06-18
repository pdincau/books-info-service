import com.rabbitmq.client.*;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookInfoService {

    static final Logger LOG = LoggerFactory.getLogger(BookInfoService.class);

    private final static String QUEUE_NAME = "book_events";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        LOG.info("Waiting for events from queue.");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                LOG.info("Received message: {}", message);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
