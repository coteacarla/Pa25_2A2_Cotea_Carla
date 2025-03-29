package shell;

import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.InvalidArgLength;
import models.Image;
import repository.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SaveCommand implements Command {

    public void execute(String[] args, Repository repository) {
        if (args.length < 2) {
            System.out.println("Usage: save path <text|binary|json>");
            throw new InvalidArgLength("Usage: save path <text|binary|json>");
        }

        String filePath = args[0];
        String format = args[1].toLowerCase();

        try {
            switch (format) {
                case "text":
                    saveAsText(filePath,repository);
                    break;
                case "binary":
                    saveAsBinary(filePath,repository);
                    break;
                case "json":
                    saveAsJson(filePath,repository);
                    break;
                default:
                    System.out.println("Invalid format! Use text, binary, or json.");
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private void saveAsText(String filePath, Repository repository) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            for (Image image : repository.getAllImages()) {
                writer.write(image.path() + "\n");
            }
        }
        System.out.println("Saved repository to text file: " + filePath);
    }

    private void saveAsBinary(String filePath, Repository repository) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(repository.getAllImages());
        }
        System.out.println("Saved repository to binary file: " + filePath);
    }

    private void saveAsJson(String filePath, Repository repository) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(filePath), repository.getAllImages());
        System.out.println("Saved repository to JSON file: " + filePath);
    }
}