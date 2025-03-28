package shell;

import exceptions.DuplicateImageException;
import exceptions.ImageNotFoundException;
import exceptions.InvalidArgLength;
import models.Image;
import repository.Repository;

public class UpdateCommand implements Command {
    public void execute(String[] args, Repository repository) {

        if(args.length < 3 && args.length>3) //prevname+newname+path
        throw new InvalidArgLength("Incorrect number of arguments");

        if(args.length==3)
        {
            String prevname=args[0];
            String newname=args[1];
            String newpath=args[2];
            try {
                Image image=repository.findImageByName(prevname);
                if(image==null)
                    throw new ImageNotFoundException("Image not found");
                repository.remove(image);
                Image newimage=repository.findImageByName(newname);
                if(newimage==null)
                    repository.add(newimage);
                else
                    throw new DuplicateImageException("Image with name "+newname+" already exists");

            } catch (ImageNotFoundException e) {
                throw new RuntimeException(e);
            } catch (DuplicateImageException e) {
                throw new RuntimeException(e);
            }


        }

    }
}
