package exceptions;

public class InvalidTypeOfArgs extends RuntimeException {
    public InvalidTypeOfArgs(String message) {
        super(message);
    }
}
