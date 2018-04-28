package org.jfugue.examples.website;

import org.jfugue.devtools.DiagnosticParserListener;
import org.jfugue.player.Player;
import org.jfugue.temporal.TemporalPLP;
import org.staccato.StaccatoParser;

public class TemporalExample {
    private static final String MUSIC = "C D E F G A B";  // Feel free to put your own music here to experiment!
    private static final long TEMPORAL_DELAY = 500;       // Feel free to put your own delay here to experiment!
    
    public static void main(String[] args) {
        // Part 1. Parse the original music
        StaccatoParser parser = new StaccatoParser();
        TemporalPLP plp = new TemporalPLP();
        parser.addParserListener(plp);
        parser.parse(MUSIC);
        
        // Part 2. Send the events from Part 1, and play the original music with a delay
        DiagnosticParserListener dpl = new DiagnosticParserListener();
        plp.addParserListener(dpl);
        new Player().delayPlay(TEMPORAL_DELAY, MUSIC);
        plp.parse();
    }
}

