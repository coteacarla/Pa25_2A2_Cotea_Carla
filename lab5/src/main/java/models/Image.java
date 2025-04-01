package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record Image(String name,  String path, LocalDate date, List<String> tags) implements Serializable {
    private static final long serialVersionUID = 1L; // Required for serialization
    public Image(String name, String path) {
        this(name, path, LocalDate.now(), List.of());
    }

    public Image(String name, String path, LocalDate date) {
        this(name, path, date, List.of());
    }

    public Image(String name, String path, LocalDate date, List<String> tags) {
        this.name = name;
        this.path = path;
        this.date = date;
        this.tags = tags;
    }
    public Image(String name, String path, List<String> tags) {
        this(name, path, LocalDate.now(), tags);
    }
}