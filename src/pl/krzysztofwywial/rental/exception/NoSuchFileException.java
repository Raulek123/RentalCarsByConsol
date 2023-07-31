package pl.krzysztofwywial.rental.exception;

public class NoSuchFileException extends RuntimeException {
    public NoSuchFileException(String message) {
        super(message);
    }
}
