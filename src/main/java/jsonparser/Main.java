package jsonparser;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String json1 = """
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
        String json2 = """
            {
              "name": "Bob",
              "note": "note",
              "address":{
                "city": "London"
              },
              "scores": 10,
              "active": true
            }
            """;
        Lexer lexer = new Lexer(json2);
        List<Token> tokens = lexer.tokenize();
        Parser parser = new Parser(tokens);
        Map<String, Object> map = (Map<String, Object>) parser.parse();
       for(Map.Entry<String, Object> entry: map.entrySet()){
           System.out.println(entry.getKey() + ":" + entry.getValue());
       }
    }
}