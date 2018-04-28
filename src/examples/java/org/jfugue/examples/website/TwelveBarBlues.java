package org.jfugue.examples.website;

import java.io.IOException;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.theory.ChordProgression;

public class TwelveBarBlues {
    public static void main(String[] args) throws IOException {
        Pattern pattern = new ChordProgression("I IV V")
                .distribute("7%6")
                .allChordsAs("$0 $0 $0 $0 $1 $1 $0 $0 $2 $1 $0 $0")
                .eachChordAs("$0ia100 $1ia80 $2ia80 $3ia80 $4ia100 $3ia80 $2ia80 $1ia80")
                .getPattern()
                .setInstrument("Acoustic_Bass")
                .setTempo(100);
        new Player().play(pattern);
    }
}

