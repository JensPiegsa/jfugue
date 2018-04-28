/*
 * JFugue, an Application Programming Interface (API) for Music Programming
 * http://www.jfugue.org
 *
 * Copyright (C) 2003-2016 David Koelle
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.staccato;

import java.util.ArrayList;
import java.util.List;

import org.jfugue.pattern.Pattern;
import org.jfugue.pattern.PatternProducer;
import org.jfugue.pattern.Token;
import org.jfugue.pattern.Token.TokenType;

/**
 *  Certain functionality of Patterns depend on being able to know what each token
 *  parses as. This class provides a bridge between Patterns and Tokens and the
 *  StaccatoParser. While the methods below could be a part of either StaccatoParser
 *  or Pattern, placing them here helps keep those two classes cleaner.
 *  
 *  @see StaccatoParser
 *  @see Pattern
 *  @see Token
 *
 */
public class StaccatoParserPatternHelper 
{
    private StaccatoParser parser;
    
    public StaccatoParserPatternHelper() {
        this.parser = new StaccatoParser();
    }
    
    public List<Token> getTokens(PatternProducer p) {
        String[] tokenStrings = parser.preprocessAndSplit(p.toString());
        
        List<Token> retVal = new ArrayList<Token>();
        for (String tokenString : tokenStrings) {
            retVal.add(new Token(tokenString, getTokenType(tokenString)));
        }
        
        return retVal;
    }

    public TokenType getTokenType(String tokenString) {
        for (Subparser sub : parser.getSubparsers()) {
            if (sub.matches(tokenString)) {
                return sub.getTokenType(tokenString);
            }
        }
        return TokenType.UNKNOWN_TOKEN;
    }
}
