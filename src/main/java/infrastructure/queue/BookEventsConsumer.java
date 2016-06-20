package infrastructure.queue;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import domain.ViewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class BookEventsConsumer extends DefaultConsumer {

    static final Logger LOG = LoggerFactory.getLogger(BookEventsConsumer.class);
    private final ViewRepository repository;

    public BookEventsConsumer(Channel channel, ViewRepository repository) {
        super(channel);
        this.repository = repository;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)  throws IOException {
        String message = new String(body, "UTF-8");
        LOG.info("Processing received message: {}", message);
        repository.insert(message);
    }
}