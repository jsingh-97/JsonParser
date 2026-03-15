package jsonparser;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private int pos;
    private final String input;
    public Lexer(String input){
        this.input = input;
        this.pos = 0;
    }
    public List<Token> tokenize(){
        List<Token> tokens = new ArrayList<>();
        while(pos < input.length()){
            skipWhiteSpaces();
            if (pos >= input.length()) break;
            char ch = current();
            switch (ch){
                case '{' -> {tokens.add(new Token(Token.Type.LBRACE, "{")); advance();}
                case '}' -> {tokens.add(new Token(Token.Type.RBRACE, "}")); advance();}
                case '[' -> {tokens.add(new Token(Token.Type.LBRACKET, "[")); advance();}
                case ']' -> {tokens.add(new Token(Token.Type.RBRACKET, "]")); advance();}
                case ':' -> {tokens.add(new Token(Token.Type.COLON, ":")); advance();}
                case ',' -> {tokens.add(new Token(Token.Type.COMMA, ",")); advance();}
                case '"' -> tokens.add(readString());
                default -> {
                    if(ch == '-' || Character.isDigit(ch)){
                        tokens.add(readNumber());
                    }else if(Character.isLetter(ch)){
                        tokens.add(readLiteral());
                    }else{
                        throw new JsonParseException("Unexpected literal", pos);
                    }
                }
            }
        }
        tokens.add(new Token(Token.Type.EOF, null));
        return tokens;
    }

    private Token readLiteral(){
        int start = pos;
        while(pos < input.length() && Character.isLetter(current())){
            advance();
        }
        String literal = input.substring(start, pos);
        return switch (literal){
            case "true" -> new Token(Token.Type.BOOLEAN, "true");
            case "false" -> new Token(Token.Type.BOOLEAN, "false");
            case "null"  -> new Token(Token.Type.NULL,    "null");
            default -> throw new JsonParseException("Unknown literal '" + literal + "'", pos);
        };
    }
    private Token readNumber(){
        int start = pos;
        if(current() == '-'){
            advance();
        }
        while(pos < input.length() && Character.isDigit(current())){
            advance();
        }
        if(pos < input.length() && current() == '.'){
            advance();
            while(pos < input.length() && Character.isDigit(current())){
                advance();
            }
        }
        return new Token(Token.Type.NUMBER, input.substring(start, pos));
    }
    private Token readString(){
        //skip "
        advance();
        skipWhiteSpaces();
        StringBuilder sb = new StringBuilder();
        while(pos < input.length() && current() != '"'){
            sb.append(current());
            advance();
        }
        advance();
        return new Token(Token.Type.STRING, sb.toString());
    }
    private void skipWhiteSpaces() {
        while(pos < input.length() && Character.isWhitespace(input.charAt(pos))){
            advance();
        }
    }
    private char current(){
        return input.charAt(pos);
    }
    private void advance() {
        pos++;
    }
}

