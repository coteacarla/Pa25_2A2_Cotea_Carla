package shell;

import exceptions.DuplicateImageException;
import exceptions.ImageNotFoundException;
import exceptions.InvalidArgLength;
import models.Image;
import repository.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AddCommand implements Command {
    private static final List<String> TAGS = List.of("nature", "portrait", "architecture", "travel", "abstract", "animals", "sports", "food", "technology", "fashion");
    private static final Random RANDOM = new Random();

    public void execute(String[] args, Repository repository){
        if(args.length != 2) //name+path
            throw new InvalidArgLength("Wrong number of arguments");
        int nrTags = 1 + RANDOM.nextInt(TAGS.size());
        List<String> shuffledTags = new ArrayList<>(TAGS);
        Collections.shuffle(shuffledTags);
        List<String> randomTags = shuffledTags.subList(0, nrTags);

        Image image = new Image(args[0], args[1], LocalDate.now(), randomTags);
        try {
            repository.add(image);
        } catch (DuplicateImageException e) {
            throw new RuntimeException(e);
        }

    }
}