import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.spotify.apollo.Environment;
import com.spotify.apollo.RequestContext;
import com.spotify.apollo.Response;
import com.spotify.apollo.httpservice.HttpService;
import com.spotify.apollo.httpservice.LoadingException;
import com.spotify.apollo.route.Route;
import domain.CommandGetViews;
import domain.EventHandler;
import domain.ViewRepository;
import infrastructure.persistence.InMemoryViewRepository;
import infrastructure.queue.RabbitMQEventHandler;
import okio.ByteString;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static com.spotify.apollo.Status.*;
import static okio.ByteString.encodeUtf8;

public class BookInfoService {

    static final Logger LOG = LoggerFactory.getLogger(BookInfoService.class);

    private static final ViewRepository repository = InMemoryViewRepository.getInstance();

    public static void main(String[] args) throws LoadingException {
        EventHandler eventHandler = new RabbitMQEventHandler(repository);
        eventHandler.listen();
        HttpService.boot(BookInfoService::init, "book-info", args);
    }

    static void init(Environment environment) {
        environment.routingEngine()
                .registerAutoRoute(Route.sync("GET", "/books", BookInfoService::books))
                .registerAutoRoute(Route.sync("GET", "/ping", context -> "pong"));
    }

    private static Response<ByteString> books(RequestContext context)  {
        LOG.info("Received request to retrieve all books");
        String title = context.request().parameter("title").orElse("");
        CommandGetViews getViews = new CommandGetViews(repository, title);
        List<String> views = getViews.execute();
        String body = new Gson().toJson(views);
        LOG.info("views are: {}", body);
        return Response.forStatus(OK).withHeaders(headers()).withPayload(encodeUtf8(body));
    }

    private static Map<String, String> headers() {
        return ImmutableMap.<String, String>builder()
                    .put("Content-Type", "application/json")
                    .put("charset", "utf8")
                    .build();
    }
}
