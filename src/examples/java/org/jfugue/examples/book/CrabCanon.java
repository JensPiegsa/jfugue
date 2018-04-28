package org.jfugue.examples.book;

import org.jfugue.pattern.Pattern;
import org.jfugue.pattern.Token;
import org.jfugue.player.Player;
import org.jfugue.theory.Note;

public class CrabCanon {
    public static void main(String[] args) {
        // Upper melody of Bach’s Crab Canon
        Pattern melody1 = new Pattern("D5h E5h A5h Bb5h C#5h Rq A5q "+
                       "A5q Ab5h G5q G5q F#5h F5q F5q E5q Eb5q D5q "+
                       "C#5q A5q D5q G5q F5h E5h D5h F5h A5i G5i A5i "+
                       "D6i A5i F5i E5i F5i G5i A5i B5i C#6i D6i F5i "+
                       "G5i A5i Bb5i E5i F5i G5i A5i G5i F5i E5i F5i "+
                       "G5i A5i Bb5i C6i Bb5i A5i G5i A5i Bb5i C6i D6i "+
                       "Eb6i C6i Bb5i A5i B5i C#6i D6i E6i F6i D6i "+
                       "C#6i B5i C#6i D6i E6i F6i G6i E6i A5i E6i D6i "+
                       "E6i F6i G6i F6i E6i D6i C#6i D6q A5q F5q D5q");

        // Create second melody using a simple reverse
        Pattern melody2 = new Pattern();
        for (Token token : melody1.getTokens()) {
            Note note = new Note(token.toString()).changeValue(-Note.OCTAVE);
            melody2.prepend(note);
        }
        
        melody1.setVoice(0).setInstrument("Piano");
        melody2.setVoice(1).setInstrument("Piano");
        Player player = new Player();
        player.play(melody1, melody2);
    }
}
