package models;

import java.io.Serializable;
import java.time.LocalDate;

public record Image(String name,  String path, LocalDate date) implements Serializable {
    private static final long serialVersionUID = 1L; // Required for serialization
    public Image(String name, String path) {
        this(name, path, LocalDate.now());
    }
}