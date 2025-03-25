package org.example;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;



class ImageRepository {
    private final List<Image> images;

    public ImageRepository() {
        this.images = new ArrayList<>();
    }

    public void addImage(Image image) {
        images.add(image);
    }

    public void displayImage(String imageName) {
        for (Image image : images) {
            if (image.name().equals(imageName)) {
                File file = new File(image.filePath());
                if (file.exists()) {
                    try {
                        Desktop.getDesktop().open(file);
                    } catch (Exception e) {
                        System.out.println("Error opening file: " + e.getMessage());
                    }
                } else {
                    System.out.println("File not found: " + image.filePath());
                }
                return;
            }
        }
        System.out.println("Image not found in repository: " + imageName);
    }

    public void listImages() {
        for (Image image : images) {
            System.out.println("Name: " + image.name() + ", Date: " + image.date() + ", Tags: " + image.tags() + ", Path: " + image.filePath());
        }
    }
}

