package infrastructure.persistence;

import domain.ViewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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
}
