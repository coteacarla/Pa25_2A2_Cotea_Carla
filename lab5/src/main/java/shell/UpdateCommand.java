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
        System.out.println("Updating image with name " + args[0] + " to " + args[1] + " with length " + args[2] );

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
                Image newimage=new Image(newname,newpath);
                repository.add(newimage);


            } catch (ImageNotFoundException e) {
                throw new RuntimeException(e);
            } catch (DuplicateImageException e) {
                throw new RuntimeException(e);
            }


        }

    }
}
