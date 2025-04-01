package shell;

import exceptions.DuplicateImageException;
import exceptions.InvalidArgLength;
import models.Image;
import repository.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

public class AddAllCommand implements Command {

    public void execute(String[] args, Repository repository) {
        if (args.length != 1) {
            throw new InvalidArgLength("Usage: addAll <directory-path>");
        }
        String folderPath = args[0];

        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".jpg") || path.toString().endsWith(".png"))
                    .forEach(path -> {
                        String name = path.getFileName().toString();
                        try {
                            repository.add(new Image(name, path.toString(), LocalDate.now()));
                        } catch (DuplicateImageException e) {
                            System.out.println("Image already exists: " + name);
                        }
                    });
        } catch (IOException e) {
            System.out.println("Error reading files: " + e.getMessage());
        }
    }
}