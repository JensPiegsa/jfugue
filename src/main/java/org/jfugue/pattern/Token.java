package org.jfugue.pattern;

public class Token implements PatternProducer 
{
    private String string;
    private TokenType type;

    public Token(String string, TokenType type) {
        this.string = string;
        this.type = type;
    }
    
    /** 
     * Involves the Staccato parsers to figure out what type of token this is
     */
    public TokenType getType() {
        return this.type;
    }
    
    @Override
    public String toString() {
        return this.string;
    }
    
    @Override
    public Pattern getPattern() {
        return new Pattern(string);
    }
    
    public enum TokenType { 
        VOICE, LAYER, INSTRUMENT, TEMPO, KEY_SIGNATURE, TIME_SIGNATURE, 
        BAR_LINE, TRACK_TIME_BOOKMARK, TRACK_TIME_BOOKMARK_REQUESTED, 
        LYRIC, MARKER, FUNCTION, NOTE,
        WHITESPACE, ATOM,
        UNKNOWN_TOKEN };
}
