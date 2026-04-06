package jsonparser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    private List<Token> tokens;
    private int pos;

    public Parser(List<Token> tokens){
        this.tokens = tokens;
        this.pos = 0;
    }

    public Object parse(){
        Object result = parseValue();
        if(current().getType() != Token.Type.EOF){
            throw new JsonParseException("Unexpected token after root value :" + current(), pos);
        }
        return result;
    }

    private Object parseValue() {
        Token token = current();
        return switch (token.getType()){
            case LBRACE -> parseObject();
            case STRING -> { advance();yield token.getValue();}
            case BOOLEAN -> {advance();yield "true".equals(token.getValue()) ? Boolean.TRUE: Boolean.FALSE;}
            default -> throw new JsonParseException("Unexpected token :" + token, pos);
        };
    }

    private Map<String, Object> parseObject() {
        consume(Token.Type.LBRACE);
        Map<String, Object> map = new LinkedHashMap<>();
        parseKeyValuePair(map);
        while(current().getType() == Token.Type.COMMA){
            advance();
            if(current().getType() == Token.Type.RBRACE){
                throw new JsonParseException("Trailing comma in object", pos);
            }
            parseKeyValuePair(map);
        }
        consume(Token.Type.RBRACE); //expect and skip }
        return map;
    }

    private void parseKeyValuePair(Map<String, Object> map){
        Token keyToken = current();
        if(keyToken.getType() != Token.Type.STRING){
            throw new JsonParseException("Expected String key in object, got " + keyToken, pos);
        }
        String key = keyToken.getValue();
        advance();
        consume(Token.Type.COLON);
        Object value = parseValue();
        map.put(key, value);
    }

    private void consume(Token.Type expectedType){
        Token token = current();
        if(token.getType() != expectedType){
            throw new JsonParseException("Expected " + expectedType + " but got" + token.getType(), pos);
        }
        advance();
    }
    private Token current(){
        return tokens.get(pos);
    }
    private void advance(){
        this.pos++;
    }

}
