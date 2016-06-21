package domain;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static java.util.Collections.emptyList;

public class CommandGetViews extends HystrixCommand<List<String>> {

    private static ViewRepository repository;
    private final String title;

    public CommandGetViews(ViewRepository repository, String title) {
        super(HystrixCommandGroupKey.Factory.asKey("BookInfoGroup"));
        this.repository = repository;
        this.title = title;
    }

    @Override
    protected List<String> run() throws Exception {
        if (StringUtils.isBlank(title)) {
            return repository.all();
        }
        return repository.findBy(title);
    }

    @Override
    protected List<String> getFallback() {
        return emptyList();
    }

}
