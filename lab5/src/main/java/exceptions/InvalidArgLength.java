package exceptions;

public class InvalidArgLength extends RuntimeException {
    public InvalidArgLength(String message) {
        super(message);
    }
}
