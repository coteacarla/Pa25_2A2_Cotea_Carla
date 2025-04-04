package shell;

import repository.Repository;

public class LoadCommand implements Command {

    public void execute(String[] args, Repository repository) {
        if (args.length != 1) {
            System.out.println("Usage: load <file-path>");
            return;
        }

        String filePath = args[0];

        try {
            repository.getRepositoryService().load(filePath);
            System.out.println("Repository loaded from: " + filePath);
        } catch (Exception e) {
            System.out.println("Error loading the repository: " + e.getMessage());
        }
    }
}
