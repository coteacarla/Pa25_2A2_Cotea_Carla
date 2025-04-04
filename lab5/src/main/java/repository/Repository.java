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
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Repository implements Serializable {
    private static final long serialVersionUID = 1L; // Required for serialization
    private final List<Image> images = new ArrayList<>();
    private RepositoryService repositoryService= new RepositoryService();
    private static final List<String> TAGS = List.of("nature", "portrait", "architecture", "travel", "abstract", "animals", "sports", "food", "technology", "fashion");
    private static final Random RANDOM = new Random();

    public RepositoryService getRepositoryService() {
        return repositoryService;
    }

    public Repository() {}

    public void add(Image image) throws DuplicateImageException{
        if(images.contains(image)) {
            throw new DuplicateImageException("Image already exists" +image.name());
        }
        int nrTags = 1 + RANDOM.nextInt(TAGS.size());
        List<String> shuffledTags = new ArrayList<>(TAGS);
        Collections.shuffle(shuffledTags);
        List<String> randomTags = shuffledTags.subList(0, nrTags);
        images.add(new Image(image.name(), image.path(), image.date(), randomTags));
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
    public List<Image> getAllImages()
    {
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