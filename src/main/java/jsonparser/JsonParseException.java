package jsonparser;

public class JsonParseException extends RuntimeException{

    public JsonParseException(String message, int position) {
        super(message + " (at position " + position + ")");
    }
}

