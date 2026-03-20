package jsonparser;

public class Token {

    enum Type{
        LBRACE, //{
        RBRACE, //}
        LBRACKET, //[
        RBRACKET, //]
        COMMA, //,
        COLON, //:
        STRING, //"key"
        NUMBER, //23, -29
        BOOLEAN,
        NULL,
        EOF
    }
    private final Type type;
    private final String value;

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}

