package org.jfugue.pattern;

import java.util.ArrayList;
import java.util.List;

import org.jfugue.pattern.Token.TokenType;

public class TokenDemo {
    public static void main(String[] args) {
        Pattern pattern = new Pattern("V0 I[Piano] A B C V1 I[Flute] D E F");
        List<Token> tokens = pattern.getTokens();
        for (Token token : tokens) {
            if (token.getType() == TokenType.INSTRUMENT) {
                System.out.println(token);
            }
        }
    }
}
