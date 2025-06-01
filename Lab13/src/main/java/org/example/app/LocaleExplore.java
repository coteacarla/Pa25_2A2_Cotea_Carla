package org.example.app;

import org.example.com.DisplayLocales;
import org.example.com.Info;
import org.example.com.SetLocale;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LocaleExplore {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Locale currentLocale = Locale.getDefault();
        ResourceBundle[] messages = { ResourceBundle.getBundle("res.Messages", currentLocale) };

        while (true) {
            System.out.print(messages[0].getString("prompt") + " ");
            String command = scanner.nextLine().trim();

            switch (command) {
                case "locales":
                    DisplayLocales.execute(messages[0]);
                    break;
                case "set":
                    System.out.print("Locale code (e.g. ro, en): ");
                    String code = scanner.nextLine().trim();
                    currentLocale = SetLocale.execute(code, messages);
                    break;
                case "info":
                    Info.execute(currentLocale, messages[0]);
                    break;
                case "exit":
                    return;
                default:
                    System.out.println(messages[0].getString("invalid"));
            }
        }
    }
}

