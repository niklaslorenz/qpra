package qpra.parser.error;

public class ParseError extends RuntimeException {

    public ParseError(String message) {
        super(message);
    }

    public ParseError(String message, Exception cause) {
        super(message, cause);
    }

}
