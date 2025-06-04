package org.example.com;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class Info {
    public static void execute(Locale locale, ResourceBundle messages) {
        System.out.println(messages.getString("info").replace("{0}", locale.toString()));

        System.out.println(messages.getString("country") + ": " +
                locale.getDisplayCountry(locale) + " (" + locale.getDisplayCountry() + ")");
        System.out.println(messages.getString("language") + ": " +
                locale.getDisplayLanguage(locale) + " (" + locale.getDisplayLanguage() + ")");

        try {
            Currency currency = Currency.getInstance(locale);
            System.out.println(messages.getString("currency") + ": " +
                    currency.getCurrencyCode() + " (" + currency.getDisplayName(locale) + ")");
        } catch (Exception e) {
            System.out.println(messages.getString("currency") + ": N/A");
        }

        String[] weekdays = new DateFormatSymbols(locale).getWeekdays();
        System.out.print(messages.getString("weekdays") + ": ");
        boolean first = true;
        for (int i = java.util.Calendar.SUNDAY; i <= java.util.Calendar.SATURDAY; i++) {
            if (!weekdays[i].isEmpty()) {
                if (!first) System.out.print(", ");
                System.out.print(weekdays[i]);
                first = false;
            }
        }
        System.out.println();

        String[] months = new DateFormatSymbols(locale).getMonths();
        System.out.print(messages.getString("months") + ": ");
        first = true;
        for (String month : months) {
            if (!month.isEmpty()) {
                if (!first) System.out.print(", ");
                System.out.print(month);
                first = false;
            }
        }
        System.out.println();

        DateFormat dfDefault = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        DateFormat dfLocale = DateFormat.getDateInstance(DateFormat.LONG, locale);
        Date today = new Date();
        System.out.println(messages.getString("today") + ": " +
                dfDefault.format(today) + " (" + dfLocale.format(today) + ")");
    }
}
