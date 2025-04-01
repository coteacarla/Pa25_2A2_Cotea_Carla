package shell;

import exceptions.DuplicateImageException;
import exceptions.ImageNotFoundException;
import exceptions.InvalidArgLength;
import models.Image;
import repository.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class UpdateCommand implements Command {
    private static final List<String> TAGS = List.of("nature", "portrait", "architecture", "travel", "abstract", "animals", "sports", "food", "technology", "fashion");
    private static final Random RANDOM = new Random();
    public void execute(String[] args, Repository repository) {
        if(args.length < 3 && args.length>3){//prevname+newname+path
            throw new InvalidArgLength("Incorrect number of arguments");
        }

        System.out.println("Updating image with name " + args[0] + " to " + args[1] + " with length " + args[2] );

        if(args.length==3)
        {
            String prevName=args[0];
            String newName=args[1];
            String newPath=args[2];
            try {
                Image image=repository.findImageByName(prevName);
                if(image==null)
                    throw new ImageNotFoundException("Image not found");
                repository.remove(image);

                int nrTags = 1 + RANDOM.nextInt(TAGS.size());
                List<String> shuffledTags = new ArrayList<>(TAGS);
                Collections.shuffle(shuffledTags);
                List<String> randomTags = shuffledTags.subList(0, nrTags);

                Image newImage = new Image(newName, newPath, image.date(), randomTags);
                repository.add(newImage);

            } catch (ImageNotFoundException e) {
                throw new RuntimeException(e);
            } catch (DuplicateImageException e) {
                throw new RuntimeException(e);
            }


        }

    }
}