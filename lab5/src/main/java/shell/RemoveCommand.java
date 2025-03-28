package shell;

import exceptions.ImageNotFoundException;
import exceptions.InvalidArgLength;
import models.Image;
import repository.Repository;

public class RemoveCommand implements Command {
    public void execute(String[] args, Repository repository) {
        if(args.length != 2) //name
            throw new InvalidArgLength("Wrong number of arguments");
            Image image=new Image(args[0], args[1]);
        try {
            repository.remove(image);
        } catch (ImageNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
