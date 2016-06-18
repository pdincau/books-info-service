import domain.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookInfoService {

    static final Logger LOG = LoggerFactory.getLogger(BookInfoService.class);

    public static void main(String[] argv) {
        EventHandler eventHandler = new RabbitMQEventHandler();
        eventHandler.listen();
    }
}
