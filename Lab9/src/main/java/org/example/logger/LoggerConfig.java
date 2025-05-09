package org.example.logger;

import java.io.IOException;
import java.util.logging.*;

public class LoggerConfig {
    private static final Logger logger = Logger.getLogger("org.example");

    static{
        try{
            LogManager.getLogManager().reset();
            logger.setLevel(Level.ALL);

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.INFO);
            logger.addHandler(consoleHandler);

            FileHandler fileHandler = new FileHandler("application.log", true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        }   catch(IOException e){
            logger.log(Level.SEVERE, "Error: ", e);
        }
    }

    public static Logger getLogger(){
        return logger;
    }
}
