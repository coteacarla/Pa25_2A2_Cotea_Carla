package shell;

import exceptions.DuplicateImageException;
import exceptions.ImageNotFoundException;
import exceptions.InvalidArgLength;
import models.Image;
import repository.Repository;

public class AddCommand implements Command {
    public void execute(String[] args, Repository repository){
        if(args.length != 2) //name+path
            throw new InvalidArgLength("Wrong number of arguments");

        Image image=new Image(args[0],args[1]);
        try {
            repository.add(image);
        } catch (DuplicateImageException e) {
            throw new RuntimeException(e);
        }

    }
}
