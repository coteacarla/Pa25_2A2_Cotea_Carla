package teaching.advancedJ.homework;

import teaching.advancedJ.homework.model.Document;
import teaching.advancedJ.homework.model.DocumentType;
import teaching.advancedJ.homework.model.Order;

import java.util.List;
import java.util.stream.Collectors;

public class HomeworkDemo {

    public static void main(String[] args) {
        OrderDatabase orderDatabase = new OrderDatabase();
        printAllOrdersThatHaveDocuments(orderDatabase);
        String endString = "\n====================\n";
        System.out.println("\n ==== exerciseOne === \n" + exerciseOne(orderDatabase) + endString);
        System.out.println("\n ==== exerciseTwo === \n" + exerciseTwo(orderDatabase) + endString);
        System.out.println("\n ==== exerciseThree === \n" + exerciseThree(orderDatabase) + endString);
        System.out.println("\n ==== exerciseFour === \n" + exerciseFour(orderDatabase) + endString);
        System.out.println("\n ==== exerciseFive === \n" + exerciseFive(orderDatabase) + endString);
        System.out.println("\n ==== exerciseSix === \new" + exerciseSix(orderDatabase) + endString);
        System.out.println("\n ==== exerciseSeven === \n" + exerciseSeven(orderDatabase) + endString);
        System.out.println("\n ==== exerciseEight === \n" + exerciseEight(orderDatabase) + endString);
        System.out.println("\n ==== exerciseNine === \n" + exerciseNine(orderDatabase) + endString);
        System.out.println("\n ==== exerciseTen === \n" + exerciseTen(orderDatabase) + endString);
        System.out.println("\n ==== exerciseEleven === \n" + exerciseEleven(orderDatabase) + endString);
        System.out.println("\n ==== exerciseTwelve === \n" + exerciseTwelve(orderDatabase) + endString);
        System.out.println("\n ==== exerciseThirteen === \n" + exerciseThirteen(orderDatabase) + endString);
        System.out.println("\n ==== exerciseFourteen === \n" + exerciseFourteen(orderDatabase) + endString);
    }

    public static void printAllOrdersThatHaveDocuments(OrderDatabase orderDatabase) {
        System.out.println("\nprintAllOrderThatHaveDocuments");
        orderDatabase.findAll()
                .stream()
                .filter(order -> !order.getDocumentList().isEmpty())
                .forEach(System.out::println);
    }

    public static List<Order> exerciseOne(OrderDatabase orderDatabase) {
        // This method should return all the orders that have documents of type ID_CARD
        return orderDatabase.findAll()
                .stream()
                .filter(order -> order.getDocumentList()
                        .stream()
                        .anyMatch(document -> document.getType().equals(DocumentType.ID_CARD)))
                .collect(Collectors.toList());
    }

    public static List<Order> exerciseTwo(OrderDatabase orderDatabase) {
        // This method should return all the orders that have prices bigger than 100
        return null;
    }

    public static boolean exerciseThree(OrderDatabase orderDatabase) {
        // This method should check if orders with more than 2 documents exist;
        return false;
    }

    public static Boolean exerciseFour(OrderDatabase orderDatabase) {
        // This method should check if all the orders have at least one document
        return null;
    }

    public static List<Document> exerciseFive(OrderDatabase orderDatabase) {
        // This method should return all the documents from all the orders
        return null;
    }

    public static List<String> exerciseSix(OrderDatabase orderDatabase) {
        // This method should return all the order details
        return null;
    }

    public static List<Order> exerciseSeven(OrderDatabase orderDatabase) {
        // This method should return all the orders that have all the documents bigger than 5 mb.
        return null;
    }

    public static String exerciseEight(OrderDatabase orderDatabase) {
        // This method should update all the orders by doubling their prices
        return null;
    }

    public static Long exerciseNine(OrderDatabase orderDatabase) {
        // This method should count all the orders with bigger prices tha 250
        return null;
    }

    public static Double exerciseTen(OrderDatabase orderDatabase) {
        // This method should return the sum of all the document sizes from all the orders
        return null;
    }

    public static Long exerciseEleven(OrderDatabase orderDatabase) {
        // This method should count what is the average number of documents in a order.
        return null;
    }

    public static Long exerciseTwelve(OrderDatabase orderDatabase) {
        // This method should return the lowest number of documents that a order have.
        return null;
    }

    public static Long exerciseThirteen(OrderDatabase orderDatabase) {
        // This method should return the total sum of the prices from all the orders.
        return null;
    }

    public static Long exerciseFourteen(OrderDatabase orderDatabase) {
        // This method should return the most common DocumentType in all the orders.
        return null;
    }
}
