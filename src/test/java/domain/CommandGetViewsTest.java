package domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CommandGetViewsTest {

    public static final int LONG_DELAY = 1001;
    public static final int NO_DELAY = 0;
    private ViewRepository repository;

    @Test
    public void
    with_resource_slower_than_timeout_the_fallback_is_triggered() throws Exception {
        repository = new ViewRepositoryWith(LONG_DELAY);
        CommandGetViews command = new CommandGetViews(repository, "any title");

        List<String> result = command.execute();

        assertThat(result).hasSize(0);
    }

    @Test
    public void with_fast_resource_it_returns_the_result() throws Exception {
        repository = new ViewRepositoryWith(NO_DELAY);
        CommandGetViews command = new CommandGetViews(repository, "any title");

        List<String> result = command.execute();

        assertThat(result).hasSize(1).contains("a view");
    }


    private class ViewRepositoryWith implements ViewRepository {

        private int timeoutBeforeFallback;

        public ViewRepositoryWith(int timeoutBeforeFallback) {
            this.timeoutBeforeFallback = timeoutBeforeFallback;
        }

        @Override
        public void insert(String view) {}

        @Override
        public List<String> all() { return null; }

        @Override
        public List<String> findBy(String title) {
            try {
                Thread.sleep(timeoutBeforeFallback);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Arrays.asList("a view");
        }
    }
}