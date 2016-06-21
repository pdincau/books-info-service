package domain;

import java.util.List;

public interface ViewRepository {

    void insert(String view);

    List<String> all();

    List<String> findBy(String title);
}
