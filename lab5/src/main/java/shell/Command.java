package shell;

import exceptions.DuplicateImageException;
import repository.Repository;

public interface Command {
    void execute(String[] args, Repository repository) throws DuplicateImageException;
}
