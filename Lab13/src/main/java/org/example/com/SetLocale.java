package org.example.com;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class SetLocale {
    public static Locale execute(String languageTag, ResourceBundle[] messages) {
        Locale newLocale;

        switch (languageTag) {
            case "ro":
                newLocale = new Locale("ro", "RO");
                break;
            case "en":
                newLocale = new Locale("en", "US");
                break;
            case "fr":
                newLocale = new Locale("fr", "FR");
                break;
            case "de":
                newLocale = new Locale("de", "DE");
                break;
            default:
                newLocale = Locale.forLanguageTag(languageTag); // fallback
        }

        messages[0] = ResourceBundle.getBundle("res.Messages", newLocale);

        System.out.println(messages[0].getString("locale.set").replace("{0}", newLocale.toString()));
        return newLocale;
    }
}
