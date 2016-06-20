package infrastructure;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class BookEventsConsumer extends DefaultConsumer {

    static final Logger LOG = LoggerFactory.getLogger(BookEventsConsumer.class);

    public BookEventsConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)  throws IOException {
        String message = new String(body, "UTF-8");
        LOG.info("Received message: {}", message);
    }
}