package domain;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.Collections.emptyList;

public class CommandGetViews extends HystrixCommand<List<String>> {

    private static ViewRepository repository;
    private final String title;

    static final Logger LOG = LoggerFactory.getLogger(CommandGetViews.class);

    public CommandGetViews(ViewRepository repository, String title) {
        super(HystrixCommandGroupKey.Factory.asKey("BookInfoGroup"));
        this.repository = repository;
        this.title = title;
    }

    @Override
    protected List<String> run() throws Exception {
        LOG.info("Invoking command to retrieve views");
        if (StringUtils.isBlank(title)) {
            return repository.all();
        }
        return repository.findBy(title);
    }

    @Override
    protected List<String> getFallback() {
        LOG.info("Using fallback while applying command to retrieve views");
        return emptyList();
    }
}
