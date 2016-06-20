package infrastructure.persistence;

import domain.View;
import domain.ViewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class InMemoryViewRepository implements ViewRepository {

    static final Logger LOG = LoggerFactory.getLogger(InMemoryViewRepository.class);

    private final List<View> views;

    public InMemoryViewRepository() {
        this.views = new ArrayList<>();
    }

    @Override
    public void insert(View view) {
        LOG.info("Saving view: {}", view);
        views.add(view);
    }
}