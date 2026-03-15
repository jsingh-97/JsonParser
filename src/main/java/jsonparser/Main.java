package jsonparser;

import java.util.List;
public class Main {
    public static void main(String[] args) {
        String json = """
            {
              "name": "Bob",
              "address": {
                "city": "Toronto",
                "zip": "M5V"
              },
              "scores": [10, 20, -30],
              "active": true,
              "note": null
            }
            """;
        Lexer lexer = new Lexer(json);
        List<Token> tokens = lexer.tokenize();
        System.out.println("haha");
    }
}