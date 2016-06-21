package infrastructure.persistence;

import domain.ViewRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class InMemoryViewRepository implements ViewRepository {

    static final Logger LOG = LoggerFactory.getLogger(InMemoryViewRepository.class);

    private final List<String> views;
    private static InMemoryViewRepository instance = null;

    public static InMemoryViewRepository getInstance() {
        if (instance == null) {
            return new InMemoryViewRepository();
        }
        return instance;
    }

    public InMemoryViewRepository() {
        this.views = new ArrayList<>();
    }

    @Override
    public void insert(String view) {
        LOG.info("Saving view: {}", view);
        views.add(view);
    }

    @Override
    public List<String> all() {
        LOG.info("Retrieving all views");
        return views;
    }

    @Override
    public List<String> findBy(String title) {
        String textToSearch = "\"title\":\""+ title + "\"";
        return views.stream().filter(view -> StringUtils.contains(view, textToSearch)).collect(toList());
    }
}
