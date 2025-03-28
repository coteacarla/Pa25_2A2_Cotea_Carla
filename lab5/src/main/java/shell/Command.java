package shell;

import repository.Repository;

public interface Command {
    void execute(String[] args, Repository repository);
}
