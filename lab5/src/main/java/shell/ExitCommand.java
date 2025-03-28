package shell;

import repository.Repository;

public class ExitCommand implements Command {

    public void execute(String[] args, Repository repository) {
        System.out.println("Exiting shell...");
        System.exit(0);
    }
}
