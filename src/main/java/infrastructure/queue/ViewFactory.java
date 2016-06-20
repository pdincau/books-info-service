package infrastructure.queue;

import domain.BookDetail;
import domain.View;

public class ViewFactory {

    public static View createFrom(ViewDto viewDto) {
        BookDetail detail = viewDto.getBookDetail();
        return new View(detail.getTitle(), detail.getAuthor(), detail.getIsbn());
    }
}
