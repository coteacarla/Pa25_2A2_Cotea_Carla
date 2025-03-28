package repository;
import models.Image;
import exceptions.ImageNotFoundException;
import exceptions.DuplicateImageException;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Repository implements Serializable {
    private static final long serialVersionUID = 1L; // Required for serialization
    private final List<Image> images = new ArrayList<>();

    public Repository() {}

    public void add(Image image) throws DuplicateImageException{
        if(images.contains(image)) {
            throw new DuplicateImageException("Image already exists" +image.name());
        }
        images.add(image);
    }
    public void remove(Image image) throws ImageNotFoundException{
        Image imageToRemove = images.get(images.indexOf(image));
        if(!imageToRemove.equals(image)) {
            throw new ImageNotFoundException("Image doesn't exist" + image.name());
        }
        images.remove(image);
    }

    public Image findImageByName(String name) throws ImageNotFoundException {
        return images.stream()
                .filter(img -> img.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new ImageNotFoundException("Image not found: " + name));
    }
    public List<Image> getAllImages() {
        return new ArrayList<> (images);
    }

    public void addAll(String folderPath){
        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".jpg") || path.toString().endsWith(".png"))
                    .forEach(path -> {
                        String name = path.getFileName().toString();
                        try{
                            add(new Image(name, path.toString(), LocalDate.now()));
                        } catch (DuplicateImageException e) {
                            System.out.println("Image already exists" + name);
                        }
                    });
        } catch(IOException e){
            System.out.println("Error reading files" + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return images.toString();
    }
}