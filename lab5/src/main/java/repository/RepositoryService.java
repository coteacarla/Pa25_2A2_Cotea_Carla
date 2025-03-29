package repository;

import models.Image;
import exceptions.InvalidRepositoryException;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RepositoryService {

    public void save(Repository repo, String path) throws IOException {
        try (var oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(repo);
        }
    }

    public Repository load(String path) throws InvalidRepositoryException {
        if (!Files.exists(Paths.get(path))) {
            throw new InvalidRepositoryException("Repository file not found: " + path);
        }
        try (var ois = new ObjectInputStream(new FileInputStream(path))) {
            return (Repository) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new InvalidRepositoryException("Error loading repository: " + e.getMessage());
        }
    }

    public void view(Image img) {
        try {
            Desktop desktop = Desktop.getDesktop();
            File file = new File(img.path());
            if (file.exists()) {
                desktop.open(file);
            } else {
                System.out.println("Image file not found: " + img.path());
            }
        } catch (IOException e) {
            System.out.println("Error opening image: " + e.getMessage());
        }
    }
}