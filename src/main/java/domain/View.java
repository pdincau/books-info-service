package domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class View {

    String title;
    String author;
    String isbn;

    public View(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
