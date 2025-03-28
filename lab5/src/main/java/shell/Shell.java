package shell;

import exceptions.UnknownCommandException;
import repository.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Shell {
    private final Map<String, Command> commands;
    Repository repository;
    public Shell(Repository repository) {
        repository = repository;
        commands = new HashMap<>();
        commands.put("add",new AddCommand());
        commands.put("remove",new RemoveCommand());
        commands.put("load",new LoadCommand());
        commands.put("update",new UpdateCommand());
        commands.put("report",new ReportCommand());
        commands.put("exit",new ExitCommand());
    }
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("shell> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] parts = input.split(" ");
            String commandName = parts[0];
            String[] args = new String[parts.length - 1];
            System.arraycopy(parts, 1, args, 0, parts.length - 1);
            if(commands.containsKey(commandName)) {
                commands.get(commandName).execute(args);
            }
            else {
                throw new UnknownCommandException(commandName);
            }
        }
    }
}
