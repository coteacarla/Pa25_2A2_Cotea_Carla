package exceptions;

public class DuplicateImageException extends Exception {
    public DuplicateImageException(String message) {
        super(message);
    }
}