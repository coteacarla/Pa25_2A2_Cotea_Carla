package org.example;

import repository.Repository;
import shell.Shell;

public class Main {
    public static void main(String[] args) {
        Repository repo=new Repository();
        Shell terminal = new Shell(repo);
        terminal.run();
    }
}
